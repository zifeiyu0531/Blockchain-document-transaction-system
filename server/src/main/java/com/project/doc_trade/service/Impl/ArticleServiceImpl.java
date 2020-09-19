package com.project.doc_trade.service.Impl;

import com.project.doc_trade.dao.*;
import com.project.doc_trade.entity.*;
import com.project.doc_trade.exception.MyException;
import com.project.doc_trade.service.ArticleService;
import com.project.doc_trade.util.ListRtn;
import com.project.doc_trade.util.UpdateRemain;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.OLE2ExtractorFactory;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

// 文档市场模块service实现类
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleDao articleDao;
    @Autowired
    PurchasedArticleDao purchasedArticleDao;
    @Autowired
    StoredArticleDao storedArticleDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UpdateRemain updateRemain;
    @Autowired
    CartDao cartDao;

    /**
     * 筛选文档信息
     *
     * @param articleScreen 筛选条件
     * @return 搜索到的文档信息列表ListRtn<ArticleInfo>
     * @throws MyException 自定义异常
     */
    @Override
    public ListRtn<ArticleInfo> getArticleByScreen(ArticleScreen articleScreen) throws MyException {

        int pageSize = articleScreen.getPageSize();// 每页条数
        int pageNum = articleScreen.getPageNum();// 当前页码
        int start = (pageNum - 1) * pageSize;// 当前页的起始条数
        int nums = start + pageSize;// 当前页的结束条数（不能包含）
        try {
            ListRtn<ArticleInfo> listRtn = new ListRtn<>(0, new ArrayList<>());
            // 目录对象
            Directory directory = FSDirectory.open(new File("./indexDir"));
            // 创建读取工具
            IndexReader reader = DirectoryReader.open(directory);
            // 创建搜索工具
            IndexSearcher searcher = new IndexSearcher(reader);

            QueryParser parser;
            Query query;
            // 判断是否有文档类型,搜索框是否有值
            String keyWord = articleScreen.getKeyWord();
            String type = articleScreen.getType();
            if (type.equals("") && keyWord.equals("")) {
                query = new WildcardQuery(new Term("title", "*"));
            } else if (type.equals("")) {
                parser = new QueryParser("title", new IKAnalyzer());
                query = parser.parse(keyWord);
            } else if (keyWord.equals("")) {
                query = new WildcardQuery(new Term("type", type));
            } else {
                String[] stringQuery = {type, keyWord};
                String[] fields = {"type", "title"};
                BooleanClause.Occur[] occ = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
                query = MultiFieldQueryParser.parse(stringQuery, fields, occ, new IKAnalyzer());
            }

            // 创建排序对象,需要排序字段SortField，参数：字段的名称、字段的类型、是否反转如果是false，升序。true降序
            Sort sort = new Sort(new SortField("id", SortField.Type.LONG, false));
            // 搜索数据，查询0~end条
            TopDocs topDocs = searcher.search(query, nums, sort);
            System.out.println("本次搜索共" + topDocs.totalHits + "条数据");
            listRtn.setNums(topDocs.totalHits);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            List<ArticleInfo> articleList = new ArrayList<>();
            for (int i = start; i < nums && i < scoreDocs.length; i++) {
                ScoreDoc scoreDoc = scoreDocs[i];
                // 获取文档编号
                int docID = scoreDoc.doc;
                Document doc = reader.document(docID);

                ArticleInfo article = new ArticleInfo();
                article.setArticleId(Integer.parseInt(doc.get("id")));
                article.setArticleTitle(doc.get("title"));
                article.setArticleAuthor(doc.get("author"));
                article.setSubmissionDate(doc.get("date"));
                article.setArticlePrice(Double.parseDouble(doc.get("price")));
                article.setArticleType(doc.get("type"));
                article.setArticleAbstract(doc.get("abstract"));
                article.setArticleDownload(Integer.parseInt(doc.get("download")));
                article.setArticleStore(Integer.parseInt(doc.get("store")));

                articleList.add(article);
            }
            listRtn.setList(articleList);
            return listRtn;
        } catch (Exception e) {
            throw new MyException("查询失败：" + e.getMessage());
        }
    }

    /**
     * 按关键词搜索文档
     *
     * @param keyword 在搜索栏中输入的关键词
     * @return 搜索到的文档列表
     * @throws MyException 自定义异常
     */
    @Override
    public List<ArticleInfo> searchArticleKeyword(String keyword) throws MyException {
        return new ArrayList<>();
    }

    /**
     * 按类型搜索文档
     *
     * @param type 选择的文档类型
     * @return 搜索到的文档列表
     * @throws MyException 自定义异常
     */
    @Override
    public List<ArticleInfo> searchArticleType(String type) throws MyException {
        return new ArrayList<>();
    }

    /**
     * 获取向用户推荐的文档
     *
     * @param id 用户id
     * @return 搜索到的文档列表
     * @throws MyException 自定义异常
     */
    public List<ArticleInfo> getRecommendArticle(Serializable id) throws MyException {
        try {
            List<RecommendedItem> recommendedList = getRecommendList(Long.parseLong(String.valueOf(id)));
            System.out.println(recommendedList.toString());
            if (recommendedList.size() == 0) {
                return getRandomArticle();
            } else {
                List<ArticleInfo> articleList = new ArrayList<>();
                Set<Serializable> idSet = new HashSet<>();
                for (RecommendedItem item : recommendedList) {
                    Serializable articleId = item.getItemID();
                    if (!idSet.contains(articleId)) {
                        idSet.add(articleId);
                        ArticleInfo article = articleDao.findInfoById(item.getItemID());
                        if (article == null) {
                            throw new MyException("文档丢失");
                        } else {
                            articleList.add(article);
                        }
                    }
                }
                return articleList;
            }
        } catch (Exception e) {
            return getRandomArticle();
        }
    }

    /**
     * 调用推荐算法
     *
     * @param id 用户id
     * @return 搜索到的推荐列表
     * @throws MyException 自定义异常
     */
    private List<RecommendedItem> getRecommendList(long id) throws MyException {
        try {
            File file = new File("./dataset/ratings.dat");
            DataModel dataModel = new FileDataModel(file);

            UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
            ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(10, userSimilarity, dataModel);

            GenericUserBasedRecommender userBasedRecommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
            GenericItemBasedRecommender itemBasedRecommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);

            List<RecommendedItem> recommendedUserList = userBasedRecommender.recommend(id, 5);
            List<RecommendedItem> recommendedItemList = itemBasedRecommender.recommend(id, 5);

            recommendedUserList.addAll(recommendedItemList);
            return recommendedUserList;
        } catch (Exception e) {
            throw new MyException("获取推荐列表失败：" + e.getMessage());
        }
    }

    /**
     * 获取随机10份文档信息
     *
     * @return 获取到的文档信息
     * @throws MyException 自定义异常
     */
    private List<ArticleInfo> getRandomArticle() throws MyException {
        try {
            int count = articleDao.getCounts();
            Random random = new Random();
            List<ArticleInfo> articleList = new ArrayList<>();
            Set<Serializable> idSet = new HashSet<>();
            for (int i = 0; i < 10; i++) {
                int articleId = random.nextInt(count) + 1;
                if (!idSet.contains(articleId)) {
                    idSet.add(articleId);
                    ArticleInfo articleInfo = articleDao.findInfoById(articleId);
                    if (articleInfo == null) {
                        throw new MyException("文档丢失");
                    } else {
                        articleList.add(articleInfo);
                    }
                }
            }
            return articleList;
        } catch (Exception e) {
            throw new MyException("获取文档失败：" + e.getMessage());
        }
    }

    /**
     * 获取最热门的10篇文档
     *
     * @return 搜索到的文档列表
     * @throws MyException 自定义异常
     */
    public List<ArticleInfo> getPopularArticle() throws MyException {
        try {
            return articleDao.findByPopular();
        } catch (Exception e) {
            throw new MyException("获取文档失败：" + e.getMessage());
        }
    }

    /**
     * 获取文档详情
     *
     * @param user2Article 用户-文档对象
     * @return 文档详情
     * @throws MyException 自定义异常
     */
    @Override
    public ArticleDetail getArticleDetail(User2Article user2Article) throws MyException {
        ArticleDetail articleDetail = new ArticleDetail();
        try {
            try {
                System.out.println(user2Article.toString());
                PurchasedArticle article = purchasedArticleDao.findByUser2Article(user2Article);
                System.out.println(user2Article.toString());
                if (article == null) {
                    articleDetail.setHasPurchased(false);
                } else {
                    articleDetail.setHasPurchased(true);
                }
            } catch (Exception e) {
                throw new MyException("查询购买信息失败");
            }
            try {
                StoreArticle article = storedArticleDao.findByUser2Article(user2Article);
                if (article == null) {
                    articleDetail.setHasStored(false);
                } else {
                    articleDetail.setHasStored(true);
                }
            } catch (Exception e) {
                throw new MyException("查询收藏信息失败");
            }
            try {
                Article article = articleDao.findByUser2Article(user2Article);
                if (article == null) {
                    articleDetail.setIsAuthor(false);
                } else {
                    articleDetail.setIsAuthor(true);
                }
            } catch (Exception e) {
                throw new MyException("查询作者信息失败");
            }
            try {
                Article article = articleDao.findById(user2Article.getArticleId());
                ArticleInfo articleInfo = articleDao.findInfoById(user2Article.getArticleId());
                articleDetail.setArticleInfo(articleInfo);
                String url = article.getArticleUrl();
                articleDetail.setArticleContext(readFile(url));
            } catch (Exception e) {
                throw new MyException("查询文档信息失败");
            }
            return articleDetail;
        } catch (Exception e) {
            throw new MyException("获取文档详情失败：" + e.getMessage());
        }
    }

    /**
     * 读取部分文档内容
     *
     * @param url 文档地址
     * @return 读取的文档内容
     * @throws MyException 自定义异常
     */
    private String readFile(String url) throws MyException {
        try {
            POITextExtractor extractor = OLE2ExtractorFactory.createExtractor(new FileInputStream(url));
            StringBuilder sbf = new StringBuilder(extractor.getText());
            int length = sbf.length();
            if (length <= 0) {
                throw new MyException("文档为空");
            } else if (length < 1000) {
                sbf.delete(length / 2, length);
            } else {
                sbf.delete(1000, length);
            }
            return sbf.toString();
        } catch (Exception e) {
            throw new MyException("获取文档内容失败：" + e.getMessage());
        }
    }

    /**
     * 购买文档
     *
     * @param purchasedArticle 购买信息对象
     * @throws MyException 自定义异常
     */
    @Override
    public void purchaseArticle(PurchasedArticle purchasedArticle) throws MyException {
        int articleId = purchasedArticle.getArticleId();
        int userId = purchasedArticle.getUserId();
        try {
            Article article = articleDao.findById(articleId);
            double price;
            int authorId;
            if (article == null) {
                throw new MyException("查询文档失败！");
            } else {
                price = article.getArticlePrice();
                authorId = article.getAuthorId();
            }
            User user = userDao.findById(userId);
            User saleUser = userDao.findById(authorId);
            if (user == null || saleUser == null) {
                throw new MyException("查询用户失败！");
            } else {
                updateRemain.updatePoint(user, price, 0);
                updateRemain.updatePoint(saleUser, price, 1);
            }
            if (purchasedArticleDao.add(purchasedArticle) != 1) {
                throw new MyException("添加订单失败！");
            }
        } catch (Exception e) {
            throw new MyException("文档购买失败：" + e.getMessage());
        }
    }

    /**
     * 添加购物车
     *
     * @param user2Article 用户-文档对象
     * @throws MyException 自定义异常
     */
    @Override
    public void addCart(User2Article user2Article) throws MyException {
        int articleId = user2Article.getArticleId();
        int userId = user2Article.getUserId();
        try {
            if (articleDao.findById(articleId) == null) {
                throw new MyException("查找文档失败");
            }
            if (userDao.findById(userId) == null) {
                throw new MyException("查找用户失败");
            }
            if (cartDao.findByUser2Article(user2Article) != null) {
                throw new MyException("文档已添加");
            }
            cartDao.add(user2Article);
        } catch (Exception e) {
            throw new MyException("添加购物车失败：" + e.getMessage());
        }
    }

    /**
     * 删除购物车
     *
     * @param user2Article 用户-文档对象
     * @throws MyException 自定义异常
     */
    public void deleteCart(User2Article user2Article) throws MyException {
        try {
            if (cartDao.findByUser2Article(user2Article) == null) {
                throw new MyException("文档已删除");
            }
            cartDao.deleteByUser2Article(user2Article);
        } catch (Exception e) {
            throw new MyException("删除购物车失败：" + e.getMessage());
        }
    }

    /**
     * 查看购物车
     *
     * @param id 用户id
     * @return 购物车内文档信息
     * @throws MyException 自定义异常
     */
    public List<ArticleInfo> getCart(Serializable id) throws MyException {
        try {
            List<User2Article> articleList = cartDao.findByUserId(id);
            if (articleList == null) {
                throw new MyException("购物车为空");
            }
            List<ArticleInfo> articleInfoList = new ArrayList<>();
            for (User2Article article : articleList) {
                articleInfoList.add(articleDao.findInfoById(article.getArticleId()));
            }
            return articleInfoList;
        } catch (Exception e) {
            throw new MyException("查看购物车失败：" + e.getMessage());
        }
    }

    /**
     * 处理购物车
     *
     * @param cart 购物车对象
     * @throws MyException 自定义异常
     */
    public void dealCart(Cart cart) throws MyException {
        try {
            List<PurchasedArticle> articleList = cart.getPurchasedList();
            for (PurchasedArticle purchasedArticle : articleList) {
                this.purchaseArticle(purchasedArticle);
                this.deleteCart(purchasedArticle);
            }
        } catch (Exception e) {
            throw new MyException("处理购物车失败：" + e.getMessage());
        }
    }

    /**
     * 下载文档
     *
     * @param response HTTP请求response对象
     * @param id       文档id
     * @return 文档下载情况
     * @throws MyException 自定义异常
     */
    public String downloadArticle(HttpServletResponse response, Serializable id) throws MyException {
        try {
            if (articleDao.updateDownloadById(id) != 1) {
                throw new MyException("更新下载次数失败");
            }
            String url = articleDao.findUrlById(id);
            String[] sp = url.split("/");
            String fileName = sp[sp.length - 1];
            String downloadFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            File file = new File(url);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.setHeader("Content-Disposition", "attachment;fileName=" + downloadFileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream outputStream = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        outputStream.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "1 下载成功";
                } catch (Exception e) {
                    throw new MyException("下载失败");
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                throw new MyException("文件不存在");
            }
        } catch (Exception e) {
            throw new MyException("下载失败：" + e.getMessage());
        }
    }

    /**
     * 上传文档
     *
     * @param articleUpload 文档上传对象
     * @throws MyException 自定义异常
     */
    @Override
    public void uploadArticle(ArticleUpload articleUpload) throws MyException {
        MultipartFile file = articleUpload.getFile();
        try {

            // TODO
//            String vec = duplicateCheck(file);
//            if (vec.equals("")) {
//                throw new MyException("文档查重未通过");
//            }
            String title = articleUpload.getArticleTitle();
            saveFile(title, file);
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = formatter.format(date);
            if (articleDao.uploadArticle(articleUpload, "0", dateStr, "./dataset/article/" + title) != 1) {
                throw new MyException("数据库写入失败");
            }
            // TODO
            //addIndex(articleDao.findByVec(vec));
            User user = userDao.findById(articleUpload.getAuthorId());
            if (user == null) {
                throw new MyException("查询用户余额失败！");
            } else {
                // TODO
                //updateRemain.updatePoint(user, 10, 1);
            }
        } catch (Exception e) {
            throw new MyException("上传文档失败：" + e.getMessage());
        }
    }

    /**
     * 文档查重
     *
     * @param file 待查重文档对象
     * @return 是否重复
     * @throws MyException 自定义异常
     */
    private String duplicateCheck(MultipartFile file) throws MyException {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            String vec = getVector(text.toString());
            if (vec.equals("")) {
                throw new MyException("获取向量失败");
            }
            List<String> vecList = articleDao.findAllVec();
            for (String vecStr : vecList) {
                if (!vecStr.equals("0") && isSimilar(vec, vecStr)) {
                    throw new MyException("文档重复");
                }
            }
            return vec;
        } catch (Exception e) {
            throw new MyException("文档查重失败：" + e.getMessage());
        }
    }

    /**
     * 获取文档的向量表示
     *
     * @param text 文档内容
     * @return 文档向量表示
     * @throws MyException 自定义异常
     */
    private String getVector(String text) throws MyException {
        try {
            String[] args = new String[]{"python", "./duplicateCheck/get_vec.py", text};
            Process proc = Runtime.getRuntime().exec(args);// 执行py文件
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            if ((line = in.readLine()) != null) {
                System.out.println(line);
                return line;
            }
            System.out.println("false");
            in.close();
            proc.waitFor();
            return "";
        } catch (Exception e) {
            throw new MyException("获取向量失败：" + e.getMessage());
        }
    }

    /**
     * 获取两向量相似度
     *
     * @param vec1 向量一
     * @param vec2 向量二
     * @return 是否相似
     * @throws MyException 自定义异常
     */
    private boolean isSimilar(String vec1, String vec2) throws MyException {
        try {
            String[] args = new String[]{"python", "./duplicateCheck/get_similar.py", vec1, vec2};
            Process proc = Runtime.getRuntime().exec(args);// 执行py文件
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = in.readLine();
            if (line == null) {
                throw new MyException("无法获取相似度");
            }
            if (Double.parseDouble(line) > 0.9) {
                return true;
            }
            in.close();
            proc.waitFor();
            return false;
        } catch (Exception e) {
            throw new MyException("获取向量失败：" + e.getMessage());
        }
    }

    /**
     * 文档保存
     *
     * @param fileName 文件名
     * @param file     文件对象
     */
    private void saveFile(String fileName, MultipartFile file) throws MyException {
        try {
            String filePath = "./dataset/article/";
            File targetFile = new File(filePath + fileName+".docx");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(targetFile);
                IOUtils.copy(file.getInputStream(), fileOutputStream);
            } catch (IOException e) {
                throw new MyException("保存文件失败");
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            throw new MyException("文档保存失败：" + e.getMessage());
        }
    }

    /**
     * 添加索引
     *
     * @param article 待添加的文档对象
     * @throws MyException 自定义异常
     */
    private void addIndex(Article article) throws MyException {
        try {
            Document document = new Document();
            document.add(new StringField("id", String.valueOf(article.getArticleId()), Field.Store.YES));
            document.add(new TextField("title", article.getArticleTitle(), Field.Store.YES));
            document.add(new StringField("author", article.getArticleAuthor(), Field.Store.YES));
            document.add(new StringField("date", article.getSubmissionDate(), Field.Store.YES));
            document.add(new StringField("price", String.valueOf(article.getArticlePrice()), Field.Store.YES));
            document.add(new TextField("type", article.getArticleType(), Field.Store.YES));
            document.add(new StringField("abstract", article.getArticleAbstract(), Field.Store.YES));
            document.add(new StringField("download", String.valueOf(article.getArticleDownload()), Field.Store.YES));
            document.add(new StringField("store", String.valueOf(article.getArticleStore()), Field.Store.YES));

            Directory directory = FSDirectory.open(new File("./indexDir"));
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, analyzer);
            conf.setOpenMode(IndexWriterConfig.OpenMode.APPEND);

            IndexWriter indexWriter = new IndexWriter(directory, conf);
            indexWriter.addDocument(document);
            indexWriter.commit();
            indexWriter.close();
        } catch (Exception e) {
            throw new MyException("建立索引失败");
        }
    }

    /**
     * 收藏文档
     *
     * @param storeArticle 收藏文档对象
     * @throws MyException 自定义异常
     */
    @Override
    public void storeArticle(StoreArticle storeArticle) throws MyException {
        int articleId = storeArticle.getArticleId();
        int userId = storeArticle.getUserId();
        try {
            if (articleDao.findInfoById(articleId) == null) {
                throw new MyException("文档不存在");
            }
            if (userDao.findById(userId) == null) {
                throw new MyException("用户不存在");
            }
            if (storedArticleDao.add(storeArticle) != 1) {
                throw new MyException("添加收藏信息失败");
            }
            if (articleDao.updateStoreById(articleId) != 1) {
                throw new MyException("更新收藏数失败");
            }
        } catch (Exception e) {
            throw new MyException("收藏文档失败：" + e.getMessage());
        }
    }

    /**
     * 取消收藏
     *
     * @param storeArticle 收藏文档对象
     * @throws MyException 自定义异常
     */
    public void storeCancel(StoreArticle storeArticle) throws MyException {
        try {
            if (storedArticleDao.deleteByInfo(storeArticle) != 1) {
                throw new MyException("删除收藏信息失败");
            }
        } catch (Exception e) {
            throw new MyException("删除收藏文档失败：" + e.getMessage());
        }
    }

    /**
     * 文档评分
     *
     * @param articleRate 文档评分对象
     * @throws MyException 自定义异常
     */
    @Override
    public void rateArticle(ArticleRate articleRate) throws MyException {
        try {
            File f = new File("./dataset/ratings.dat");
            BufferedWriter output = new BufferedWriter(new FileWriter(f, true));//true,则追加写入text文本
            output.write(articleRate.getUserId() + "," + articleRate.getArticleId() + "," + articleRate.getRate() + "\n");
            output.flush();
            output.close();
        } catch (Exception e) {
            throw new MyException("文档评分失败：" + e.getMessage());
        }
    }
}
