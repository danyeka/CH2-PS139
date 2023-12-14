import pandas as pd
import numpy as np

data_file = 'user_data_transform.csv'
data_pengguna = pd.read_csv(data_file)

def recommend_top_spice(df_rempah_user,top_n=5):
    # Menghitung jumlah pengguna yang menyukai setiap rempah
    rempah_fav = df_rempah_user.iloc[:, 1:].sum()
    rempah_fav_sorted = rempah_fav.sort_values(ascending=False)
    top_5_rempah = rempah_fav_sorted.head(top_n).index.values

    return top_5_rempah

def hitung_similaritas_jaccard(dataframe, id_user1, id_user2):
    user1 = dataframe[dataframe['User_ID'] == id_user1].iloc[:, 1:].values.flatten()
    user2 = dataframe[dataframe['User_ID'] == id_user2].iloc[:, 1:].values.flatten()

    intersection = np.sum(np.logical_and(user1, user2))
    union = np.sum(np.logical_or(user1, user2))

    similaritas = intersection / union if union > 0 else 0
    return similaritas

def hitung_semua_similaritas_jaccard(dataframe, target_user_id):
    similaritas_pengguna = {}
    for user_id in dataframe['User_ID'].unique():
        if user_id != target_user_id:
            similaritas = hitung_similaritas_jaccard(dataframe, target_user_id, user_id)
            similaritas_pengguna[user_id] = similaritas
    return similaritas_pengguna

def pilih_pengguna_terdekat(similaritas_pengguna, num_users=1):
    sorted_users = sorted(similaritas_pengguna.items(), key=lambda x: x[1], reverse=True)
    top_users = sorted_users[:num_users]
    return top_users

def ambil_item_disukai_pengguna(dataframe, user_ids):
    item_disukai = set()
    for user_id, similariti in user_ids:
        if similariti == 0.0 :
            item_disukai.update(top_5_item)
        else:
            user_data = dataframe[dataframe['User_ID'] == user_id].iloc[:, 1:]
            item_disukai.update(user_data.columns[user_data.iloc[0, :] == 1])
    return item_disukai

def filter_item_belum_dilihat(dataframe, target_user_id, item_disukai_pengguna_terdekat):
    user_data_target = dataframe[dataframe['User_ID'] == target_user_id].iloc[:, 1:]
    item_belum_dilihat = user_data_target.columns[user_data_target.iloc[0, :] == 0]
    return item_disukai_pengguna_terdekat.intersection(item_belum_dilihat)

#input
top_5_item = recommend_top_spice(data_pengguna, 5)
top_5_item=set(top_5_item)

target_user_id = 2  # Ganti dengan ID pengguna yang ingin Anda rekomendasikan
similaritas_pengguna = hitung_semua_similaritas_jaccard(data_pengguna, target_user_id)

pengguna_terdekat = pilih_pengguna_terdekat(similaritas_pengguna, num_users=1)

item_disukai_pengguna_terdekat = ambil_item_disukai_pengguna(data_pengguna, [user_id for user_id in pengguna_terdekat])

item_rekomendasi = filter_item_belum_dilihat(data_pengguna, target_user_id, item_disukai_pengguna_terdekat)
print(item_rekomendasi)