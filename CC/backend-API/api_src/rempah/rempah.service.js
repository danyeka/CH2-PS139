const pool = require('../../config/database');

module.exports = {
    getAllRempah: (callback) => {
        pool.query(
            'SELECT id_rempah, image, nama_rempah, nama_latin, deskripsi FROM rempah',
            [],
            (error, results, fields) => {
                if (error) {
                    return callback(error, null);
                }
                return callback(null, results);
            }
        );
    },

    getAllResep: (callback) => {
        pool.query(
            'SELECT id_resep, nama_rempah, nama_resep, image FROM resep',
            [],
            (error, results, fields) => {
                if (error) {
                    return callback(error, null);
                }
                return callback(null, results);
            }
        );
    },

    // Detail Rempah 

    // getRempahById: (id, callback) => {
    //     pool.query(
    //         'SELECT * FROM rempah WHERE id_rempah = ?',
    //         [id],
    //         (error, results, fields) => {
    //             if (error) {
    //                 return callback(error, null);
    //             }
    //             return callback(null, results[0]);
    //         }
    //     );
    // },

    getResepById: (id, callback) => {
        pool.query(
            'SELECT * FROM resep WHERE id_resep = ?',
            [id],
            (error, results, fields) => {
                if (error) {
                    return callback(error, null);
                }

                if (results && results.length > 0) {
                    // Memproses string ingredients menjadi array
                    results[0].ingredients = results[0].ingredients.split("--").map(item => item.trim()).filter(item => item !== '');
          
                    // Memproses string steps menjadi array
                    results[0].steps = results[0].steps.split("--").map(step => step.trim()).filter(step => step !== '');
                }
                
                return callback(null, results[0]);
            }
        );
    },

    // Detail Resep Terkait Rempah

    // getResepTerkait: (id, callback) => {
    //     pool.query(
    //         `SELECT
    //             GROUP_CONCAT(
    //                 CONCAT(
    //                     '{"Nama Resep": "', r.nama_resep,
    //                     '", "Ingredients": "', r.ingredients,
    //                     '", "Steps": "', r.steps,
    //                     '", "Image": "', r.image,
    //                     '", "Related_Keyword": "', r.related_keyword, '"}'
    //                 ) SEPARATOR ';'
    //             ) AS resep_terkait
    //         FROM resep r
    //         WHERE r.nama_rempah = (
    //             SELECT nama_rempah
    //             FROM rempah
    //             WHERE id_rempah = ?
    //         )
    //         GROUP BY r.nama_rempah;
    //         `,
    //         [id],
    //         (error, results, fields) => {
    //             if (error) {
    //                 return callback(error, null);
    //             }
    
    //             const resepTerkaitArray = [];
    
    //             if (results[0].resep_terkait) {
    //                 const relatedRecipes = results[0].resep_terkait.split(';');
    //                 relatedRecipes.forEach(recipeString => {
    //                     try {
    //                         const recipeObject = JSON.parse(recipeString);
    //                         resepTerkaitArray.push(recipeObject);
    //                     } catch (err) {
    //                         console.error('Error parsing JSON:', err);
    //                     }
    //                 });
    //             }
    
    //             callback(null, resepTerkaitArray);
    //         }
    //     );
    // },

    // Detail Rempah + Resep // ----------------------
    
    getRempahById: (id, callback) => {
        pool.query(
            'SELECT ' +
            '   r.*, ' +
            '   GROUP_CONCAT(' +
            '       CONCAT(' +
            '           \'{"Id Resep": "\', resep.id_resep, ' +
            '           \'", "Nama Resep": "\', resep.nama_resep, ' +
            '           \'", "Image": "\', resep.image, ' +
            '           \'", "Related_Keyword": "\', resep.related_keyword, \'"}\'' +
            '       ) SEPARATOR \';\'' +
            '   ) AS resep_terkait ' +
            'FROM rempah r ' +
            'LEFT JOIN resep ON resep.nama_rempah = r.nama_rempah ' +
            'WHERE r.id_rempah = ? ' +
            'GROUP BY r.id_rempah;',
            [id],
            (error, results, fields) => {
                if (error) {
                    return callback(error, null);
                }
                if (results.length === 0) {
                    return callback(null, null); // Rempah not found
                }
    
                const resepTerkaitArray = [];
    
                if (results[0].resep_terkait) {
                    console.log('Raw resep_terkait:', results[0].resep_terkait);
                    const relatedRecipes = results[0].resep_terkait.split(';');
                    relatedRecipes.forEach(recipeString => {
                        try {
                            const recipeObject = JSON.parse(recipeString);
                            resepTerkaitArray.push(recipeObject);
                        } catch (err) {
                            console.error('Error parsing JSON:', err);
                        }
                    });                                
                    console.log('Parsed resep_terkait:', resepTerkaitArray);
                }
    
                const rempahData = {
                    id_rempah: results[0].id_rempah,
                    image: results[0].image,
                    nama_rempah: results[0].nama_rempah,
                    nama_latin: results[0].nama_latin,
                    deskripsi: results[0].deskripsi,
                    kandungan: results[0].kandungan,
                    manfaat: results[0].manfaat,
                    cara_penyimpanan: results[0].cara_penyimpanan,
                    masakan: results[0].masakan,
                    pengobatan_tradisional: results[0].pengobatan_tradisional,
                    minuman: results[0].minuman,
                    daerah_penghasil: results[0].daerah_penghasil,
                    
                    resep_terkait: resepTerkaitArray,
                    related_keyword: results[0].related_keyword,
                };
    
                return callback(null, rempahData);
            }
        );
    },

    // API for articles
    getAllArticles: (callback) => {
        pool.query(
            'SELECT id_artikel, judul_artikel, image FROM artikel',
            [],
            (error, results, fields) => {
                if (error) {
                    return callback(error, null);
                }
                return callback(null, results);
            }
        );
    },

    getArticlesById: (id, callback) => {
        pool.query(
            'SELECT * FROM artikel WHERE id_artikel = ?',
            [id],
            (error, results, fields) => {
                if (error) {
                    return callback(error, null);
                }
                return callback(null, results[0]);
            }
        );
    },

    // for random info
    getInfoById: (id, callback) => {
        pool.query(
            'SELECT * FROM info WHERE id_info = ?',
            [id],
            (error, results, fields) => {
                if (error) {
                    return callback(error, null);
                }
                return callback(null, results[0]);
            }
        );
    }
}