import pandas as pd
import numpy as np
from sklearn.model_selection import cross_val_score
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.metrics import make_scorer, mean_squared_error
from sklearn.neighbors import KNeighborsRegressor

# Fungsi untuk menghitung similaritas Jaccard
def hitung_similaritas_jaccard(dataframe, id_user1, id_user2):
    user1 = dataframe[dataframe['User_ID'] == id_user1].iloc[:, 1:].values.flatten()
    user2 = dataframe[dataframe['User_ID'] == id_user2].iloc[:, 1:].values.flatten()

    intersection = np.sum(np.logical_and(user1, user2))
    union = np.sum(np.logical_or(user1, user2))

    similaritas = intersection / union if union > 0 else 0
    return similaritas

# Fungsi untuk menghitung similaritas Cosine
def hitung_similaritas_cosine(dataframe, id_user1, id_user2):
    user1 = dataframe[dataframe['User_ID'] == id_user1].iloc[:, 1:].values.flatten()
    user2 = dataframe[dataframe['User_ID'] == id_user2].iloc[:, 1:].values.flatten()

    # Reshape array menjadi bentuk yang sesuai untuk cosine_similarity
    user1 = user1.reshape(1, -1)
    user2 = user2.reshape(1, -1)

    # Hitung similaritas cosine menggunakan sklearn
    similaritas = cosine_similarity(user1, user2)[0, 0]
    return similaritas

# Fungsi untuk evaluasi cross-validation
def cross_val_evaluation(dataframe, similarity_function):
    mse_scorer = make_scorer(mean_squared_error, greater_is_better=False)

    # Dapatkan daftar ID pengguna unik
    unique_user_ids = dataframe['User_ID'].unique()

    # Hitung similaritas untuk setiap pasangan pengguna
    user_pairs = [(id_user1, id_user2) for id_user1 in unique_user_ids for id_user2 in unique_user_ids if id_user1 != id_user2]
    X = np.array([[id_user1, id_user2] for id_user1, id_user2 in user_pairs])

    # Membuat target variabel (y)
    y = np.array([similarity_function(dataframe, id_user1, id_user2)
                  for id_user1, id_user2 in user_pairs])

    # Hitung MSE dengan cross-validation
    mse_scores = cross_val_score(KNeighborsRegressor(n_neighbors=1), X, y, cv=5, scoring=mse_scorer)

    return -np.mean(mse_scores)

# Contoh penggunaan
# Gantilah 'user_data_transform.csv' dengan nama file yang sesuai
data_file = 'user_data_transform.csv'
data_pengguna = pd.read_csv(data_file)

# Evaluasi Jaccard Similarity
mse_jaccard = cross_val_evaluation(data_pengguna, hitung_similaritas_jaccard)
print(f"Cross-Validation MSE for Jaccard Similarity: {mse_jaccard}")

# Evaluasi Cosine Similarity
mse_cosine = cross_val_evaluation(data_pengguna, hitung_similaritas_cosine)
print(f"Cross-Validation MSE for Cosine Similarity: {mse_cosine}")