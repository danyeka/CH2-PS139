const {
    getAllRempah,
    getRempahById,
    getAllResep,
    getResepById,
    getAllArticles,
    getArticlesById,
    getInfoById
} = require('./rempah.service');

module.exports = {
    getAllRempah: (req, res) => {
        getAllRempah((err, results) => {
            if(err) {
                console.log(err);
                return res.status(500).json({
                    error: true,
                    message: 'Internal server error'
                });
            }
            return res.status(200).json({
                error: false, 
                message: 'Rempah fetched successfully',
                listRempah: results
            });
        });
    },

    getAllResep: (req, res) => {
        getAllResep((err, results) => {
            if(err) {
                console.log(err);
                return res.status(500).json({
                    error: true,
                    message: 'Internal server error'
                });
            }
            return res.status(200).json({
                error: false, 
                message: 'Resep fetched successfully',
                listResep: results
            });
        });
    },

    getAllArticles: (req, res) => {
        getAllArticles((err, results) => {
            if(err) {
                console.log(err);
                return res.status(500).json({
                    error: true,
                    message: 'Internal server error'
                });
            }
            return res.status(200).json({
                error: false, 
                message: 'Articles fetched successfully',
                listArticles: results
            });
        });
    },

    getRempahById: (req, res) => {
        const id = req.params.id;
        getRempahById(id, (err, results) => {
            if(err) {
                console.log(err);
                return res.status(500).json({
                    error: true,
                    message: 'Internal server error'
                });
            }
            if(!results) {
                return res.status(404).json({
                    error: true,
                    message: 'Rempah not found'
                });
            }
            return res.status(200).json({
                error: false, 
                message: 'Rempah found!',
                dataRempah: results
            });
        });
    },

    getResepById: (req, res) => {
        const id = req.params.id;
        getResepById(id, (err, results) => {
            if(err) {
                console.log(err);
                return res.status(500).json({
                    error: true,
                    message: 'Internal server error'
                });
            }
            if(!results) {
                return res.status(404).json({
                    error: true,
                    message: 'Resep not found'
                });
            }
            return res.status(200).json({
                error: false, 
                message: 'Resep found!',
                dataResep: results
            });
        });
    },

    getArticlesById: (req, res) => {
        const id = req.params.id;
        getArticlesById(id, (err, results) => {
            if(err) {
                console.log(err);
                return res.status(500).json({
                    error: true,
                    message: 'Internal server error'
                });
            }
            if(!results) {
                return res.status(404).json({
                    error: true,
                    message: 'Articles not found'
                });
            }
            return res.status(200).json({
                error: false, 
                message: 'Articles found!',
                dataArtikel: results
            });
        });
    },
    
    // getResepTerkait: (req, res) => {
    //     const id = req.params.id;
        
    //     getResepTerkait(id, (error, result) => {
    //         if (error) {
    //             console.error('Error in getResepTerkait:', error);
    //             return res.status(500).json({ error: 'Internal Server Error' });
    //         }
    
    //         if (!result) {
    //             return res.status(404).json({ error: 'Rempah not found' });
    //         }
    
    //         console.log('Result from getResepTerkait:', result);
    //         res.json({ resep_terkait: result });
    //     });
    // }
    
    getHomeData: (req, res) => {
        getAllRempah((err, allRempah) => {
            if (err) {
                console.log(err);
                return res.status(500).json({
                    error: true,
                    message: 'Internal server error'
                });
            }
    
            // Mendapatkan data rempah urut dari id 1 - 5 untuk kamusrempah
            const dictionaryRempah = allRempah.slice(0, 5);
    
            // Acak data untuk rekomendasirempah
            const shuffledRempah = allRempah.sort(() => Math.random() - 0.5);
            const recommendedRempah = shuffledRempah.slice(0, 5);
    
            // Mengambil data yang dibutuhkan untuk kamusrempah
            const detailedRecommendedRempahPromises = recommendedRempah.map(item =>
                new Promise((resolve, reject) => {
                    getRempahById(item.id_rempah, (err, detailedRempah) => {
                        if (err) {
                            reject(err);
                        } else {
                            // Filter untuk hanya mendapatkan item yg diinginkan
                            const simplifiedRempah = {
                                id_rempah: detailedRempah.id_rempah,
                                nama_rempah: detailedRempah.nama_rempah,
                                image: detailedRempah.image,
                            };
            
                            resolve(simplifiedRempah);
                        }
                    });
                })
            );
    
            Promise.all(detailedRecommendedRempahPromises)
                .then(detailedRecommendedRempahList => {
                    getAllArticles( (err, articleList) => {
                        if (err) {
                            console.log(err);
                            return res.status(500).json({
                                error: true,
                                message: 'Internal server error'
                            });
                        }

                        const infoID = Math.floor(Math.random() * 11) + 1; //random info id, 11 tuh totalinfo
                        getInfoById(infoID, (err, randomInfo) => {
                            if (err) {
                                console.log(err);
                                return res.status(500).json({
                                    error: true,
                                    message: 'Internal server error'
                                });
                            }

                            return res.status(200).json({
                                error: false,
                                message: 'Home data fetched successfully',
                                randomInfo : randomInfo,
                                recommendedRempah: detailedRecommendedRempahList,
                                dictionaryRempah: dictionaryRempah,
                                articleList: articleList
                            });
                        })
                    });
                })
                .catch(err => {
                    console.log(err);
                    return res.status(500).json({
                        error: true,
                        message: 'Internal server error'
                    });
                });
        });
    }
}