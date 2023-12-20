import os
from google.cloud import storage
import tensorflow as tf
from io import BytesIO
from flask import Flask, request, jsonify
from keras.models import load_model
import numpy as np
from tensorflow.keras.applications.mobilenet_v3 import preprocess_input
from flask_sqlalchemy import SQLAlchemy

# app = Flask(__name__)
app = Flask(__name__, static_url_path='', static_folder='static')
os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = 'rempahustle-credentials.json'
storage_client = storage.Client()

app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+mysqldb://root@localhost:3306/rempahustle'
db = SQLAlchemy(app)

class Rempah(db.Model):
    id_rempah = db.Column(db.Integer, primary_key=True)
    image = db.Column(db.String(255), nullable=False)
    nama_rempah = db.Column(db.String(255), nullable=False)
    nama_latin = db.Column(db.String(255))
    deskripsi = db.Column(db.Text)
    kandungan = db.Column(db.Text)
    manfaat = db.Column(db.Text)
    cara_penyimpanan = db.Column(db.Text)
    masakan = db.Column(db.Text)
    pengobatan_tradisional = db.Column(db.Text)
    minuman = db.Column(db.Text)
    daerah_penghasil = db.Column(db.Text)
    resep_terkait = db.Column(db.Text)

def req(y_true, y_pred):
    req = tf.metrics.req(y_true, y_pred)[1]
    tf.keras.backend.get_session().run(tf.local_variables_initializer())
    return req

# Load your .h5 model
model = load_model('model-rempah.h5', custom_objects={'req': req})

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        try:
            image_bucket = storage_client.get_bucket('rempahustle-bucket')
            filename = request.json['filename']
            img_blob = image_bucket.blob('rempah-uploads/' + filename)
            img_path = BytesIO(img_blob.download_as_bytes())
        except Exception:
            respond = jsonify({'message': 'Error loading image file'})
            respond.status_code = 400
            return respond

        img = tf.keras.utils.load_img(img_path, target_size=(224, 224))
        x = tf.keras.utils.img_to_array(img)
        x = np.expand_dims(x, axis=0).astype(np.float32)
        x = preprocess_input(x)
        images = np.vstack([x])

        # Model predict
        pred_spices = model.predict(images)
        # Find the max prediction of the image
        max_prediction = np.argmax(pred_spices)

        # Print or log prediction information
        print(f"Max Prediction: {max_prediction}")

        # Check if the max prediction is above a certain threshold
        threshold = 0.2
        if max_prediction <= threshold:
            respond = jsonify({'message': 'Rempah Tidak Terdeteksi'})
            respond.status_code = 400
            return respond

        # Retrieve data from the database based on the predicted label
        predicted_label_index = np.argmax(pred_spices)
        rempah_data = Rempah.query.order_by(Rempah.id_rempah).offset(predicted_label_index).first()

        # Prepare the response
        if rempah_data:
            result = {
                "label": rempah_data.nama_rempah,
                "data": {
                    "id_rempah": rempah_data.id_rempah,
                    "nama_rempah": rempah_data.nama_rempah,
                    "nama_latin": rempah_data.nama_latin,
                    "deskripsi": rempah_data.deskripsi,
                    "kandungan": rempah_data.kandungan,
                    "manfaat": rempah_data.manfaat,
                    "cara_penyimpanan": rempah_data.cara_penyimpanan,
                    "masakan": rempah_data.masakan,
                    "pengobatan_tradisional": rempah_data.pengobatan_tradisional,
                    "minuman": rempah_data.minuman,
                    "daerah_penghasil": rempah_data.daerah_penghasil,
                    "resep_terkait": rempah_data.resep_terkait
                }
            }
        else:
            result = {'message': 'Data rempah tidak ditemukan'}

        respond = jsonify(result)
        respond.status_code = 200
        return respond

    return 'OK'

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')