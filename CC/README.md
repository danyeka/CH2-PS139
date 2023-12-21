# RempaHustle Project (Cloud Computing)
<div align=justify>

Cloud Computing team responsible to make and provide several API code in accordance to Mobile Development team needs. 

**Authors :**
| Name                              | Student ID  | Universitas   |
| ----------------------------------|-------------|---------------|
| Cherillya Rahmita Nurul Nucha     | C548BKX4446 | UIN Sunan Kalijaga |
| Albert Oktamara                   | C270BSY3070 | Universitas Mulia  |

<br>

**Requirements & Tools :**

![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
![Express.js](https://img.shields.io/badge/express.js-%23404d59.svg?style=for-the-badge&logo=express&logoColor=%2361DAFB)
![Flask](https://img.shields.io/badge/flask-%23000.svg?style=for-the-badge&logo=flask&logoColor=white)
![Google Cloud](https://img.shields.io/badge/GoogleCloud-%234285F4.svg?style=for-the-badge&logo=google-cloud&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)


**Endpoints :**
- /login  = login user
- /register = register user
- /home = get several data for home page
- /rempah = get all rempah data
- /rempah/:id = get detail rempah data specific by id
- /resep = get all resep data
- /resep/:id = get detail resep data specific by id
- /artikel = get all artikel data
- /artikel/:id = get detail artikel data specific by id
- /upload/rempah/:id = upload image -> store to gcs bucket -> get data

**Service in GCP :**
- Cloud Run = for deployment 
  - Service for API Model ML = using [flask](https://github.com/danyeka/CH2-PS139/tree/master/CC/backend-API)
  - Service for Backend API = using nodejs and expressjs
- Cloud Storage = store image
- Cloud SQL = database 

<br>

**How To deploy in Cloud Run :**
> This steps was the general step of deploying in Cloud Run. For this project, Im individually prefer using Dockerfile for deploying.
1. Create Project
2. Clone your code to cloud shell (make sure already tested in local and have no error)
3. Deploy in terminal using command :
    ```
    gcloud run deploy
    ```
4. After run command above, will be prompted several number of region and you have to choose. In this project im using 'asia-southeast2' number 9
5. Then, will prompted some question and just type 'Y'

<br>

**Fun Fact!**
> This app allows you to use your device's camera to take pictures of spices. Select a picture of the spice you want to know about, and upload it to the application. voila! then you will find out what spices are and various kinds of interesting information.  
