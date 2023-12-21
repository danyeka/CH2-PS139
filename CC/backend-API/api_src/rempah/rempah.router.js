// const { checkToken } = require('../../auth/token_validation');
const {
    getAllRempah,
    getRempahById,
    getAllResep,
    getResepById,
    getHomeData,
    getAllArticles,
    getArticlesById
} = require('./rempah.controller');

const router = require('express').Router();

router.get('/rempah', getAllRempah);
router.get('/resep', getAllResep);
router.get('/rempah/:id', getRempahById);
router.get('/resep/:id', getResepById);
router.get('/home', getHomeData);
router.get('/artikel', getAllArticles);
router.get('/artikel/:id', getArticlesById);
// router.get('/rempah/:id/resepTerkait', getResepTerkait);

module.exports = router;