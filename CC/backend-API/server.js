const express = require('express')
const app = express()

const multer = require('multer');
const axios = require('axios');
const { Storage } = require('@google-cloud/storage');
const storage = new Storage();
const bucket = storage.bucket('rempahustle-bucket');
const bodyParser = require('body-parser');
const dbPool = require('./config/database');

require('dotenv').config()
const port = process.env.APP_PORT || 5000

const userRouter = require('./api_src/users/user.router');
const rempahRouter = require('./api_src/rempah/rempah.router');

app.use(express.json());
app.use(bodyParser.json());

app.use('/', userRouter);
app.use('/', rempahRouter);

// Scan Feature
const multerGCS = multer({
    storage: multer.memoryStorage(),
});

app.post('/upload', multerGCS.single('file'), (req, res) => {
    try {
      // Akses file yang diunggah melalui req.file
      const file = req.file;
  
      // Menyimpan gambar ke GCS
      const gcsFileName = 'rempah-uploads/' + file.originalname;
      const gcsFile = bucket.file(gcsFileName);
  
      const stream = gcsFile.createWriteStream({
        metadata: {
          contentType: file.mimetype,
        },
        resumable: false,
      });
  
      stream.on('error', (err) => {
        console.error('Error uploading to GCS:', err);
        res.status(500).json({ message: 'Internal Server Error' });
      });
  
      stream.on('finish', async () => {
        console.log('File uploaded to GCS:', gcsFileName);
  
        const apiUrl = process.env.API_PREDICT_HOST;
        const mlApiResponse = await axios.post(apiUrl, { filename: file.originalname });
        console.log('API Response:', mlApiResponse.data);

        const predictionResult = mlApiResponse.data.label;

        const query = 'SELECT id_rempah, nama_rempah, deskripsi FROM rempah WHERE nama_rempah = ?';
        dbPool.query(query, [predictionResult], (error, results) => {
        if (error) {
          console.error('Error executing database query:', error);
          res.status(500).json({ message: 'Internal Server Error' });
          return;
        }

        // Pastikan bahwa hasil query tidak kosong sebelum mengakses indeksnya
        if (results.length > 0) {
          const rempahData = results[0];

          const responseToClient = {
            error: 'false',
            message: 'Yay, Rempah Found!',
            data: {
                id_rempah: rempahData.id_rempah,
                nama_rempah: rempahData.nama_rempah,
                deskripsi: rempahData.deskripsi
            },
          };

          res.status(200).json(responseToClient);
        } else {
          // Jika data tidak ditemukan
          res.status(404).json({ message: 'Data not found' });
        }
      });
    });
  
      stream.end(file.buffer);
    } catch (error) {
      console.error('Error processing request:', error);
      res.status(500).json({ message: 'Internal Server Error' });
    }
});

app.get('/search/rempah', (req, res) => {
    try {
      const keyword = req.query.q;
  
      if (!keyword) {
        return res.status(400).json({ message: 'Keyword is required for search.' });
      }
  
      // Gunakan query SQL dengan LIKE untuk mencari data rempah
      const query = 'SELECT * FROM rempah WHERE nama_rempah LIKE ?';
      const searchTerm = `%${keyword}%`;
  
      dbPool.query(query, [searchTerm], (error, results) => {
        if (error) {
          console.error('Error executing database query:', error);
          res.status(500).json({ message: 'Internal Server Error' });
          return;
        }
  
        // Pastikan bahwa hasil query tidak kosong sebelum mengirim respons
        if (results.length > 0) {
          const rempahList = results.map((rempah) => ({
            id_rempah: rempah.id_rempah,
            image: rempah.image,
            nama_rempah: rempah.nama_rempah,
            nama_latin: rempah.nama_latin,
            deskripsi: rempah.deskripsi,
            // tambahkan atribut lainnya sesuai kebutuhan
          }));
  
          const responseToClient = {
            error: 'false',
            message: 'Rempah Found',
            data: rempahList,
          };
  
          res.status(200).json(responseToClient);
        } else {
          // Jika data tidak ditemukan, sesuaikan respons sesuai kebutuhan
          res.status(404).json({ message: 'Data not found' });
        }
      });
    } catch (error) {
      console.error('Error processing request:', error);
      res.status(500).json({ message: 'Internal Server Error' });
    }
});
  

// !important! 
// you need to install the following libraries |express|[dotenv > if required]
// or run this command >> npm i express dotenv 

app.get('/' , (req , res)=>{

   res.send('hello from simple server :)');

});

app.listen(port , ()=> {
    console.log('> Server is up and running on port : ' + port);
})
