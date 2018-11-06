import com.qinghui.dao.BookDao;
import com.qinghui.domain.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 2018年11月06日  10时58分
 *
 * @Author 2710393@qq.com
 * 单枪匹马你别怕，一腔孤勇又如何。
 * 这一路，你可以哭，但是你不能怂。
 * 测试ik中文分词器
 */
public class IKAnalyzerTest {

    /**
     * 测试IK中文分词器创建索引库
     */
    @Test
    public void ikAnalyzerTest() throws Exception {

        BookDao bookDao = new BookDao();

        // 1.创建ik中文分词器对象
        Analyzer analyzer = new IKAnalyzer();

        // 2.指定索引库存储的路径
        FSDirectory directory = FSDirectory.open(new File("D:\\luceneData"));

        // 3.创建索引输出流对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 4.获取数据库数据
        List<Book> bookList = bookDao.findAll();
        List<Document> documentList = new ArrayList<>();
        for (Book book : bookList) {
            Document document = new Document();
            TextField idField = new TextField("id", book.getId() + "", Field.Store.YES);
            TextField nameField = new TextField("name", book.getName(), Field.Store.YES);
            TextField picField = new TextField("pic", book.getPic(), Field.Store.YES);
            TextField priceField = new TextField("price", book.getPrice() + "", Field.Store.YES);
            TextField descField = new TextField("description", book.getDescription(), Field.Store.NO);
            document.add(idField);
            document.add(nameField);
            document.add(picField);
            document.add(priceField);
            document.add(descField);
            documentList.add(document);
        }
        for (Document document : documentList) {
            indexWriter.addDocument(document);
        }
        indexWriter.commit();
        indexWriter.close();

    }

    /**
     * 从索引库中查找数据
     */
    @Test
    public void Search() throws Exception {
        // 1.创建分词器
        Analyzer analyzer = new IKAnalyzer();
        // 2.指定索引库路径
        FSDirectory directory = FSDirectory.open(new File("D:\\luceneData"));
        // 3.创建索引检索对象
        IndexReader indexReader =IndexReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 4.创建查询解析对象
        QueryParser queryParser = new QueryParser("name", analyzer);
        Query query = queryParser.parse("id:2");
        TopDocs topDocs = indexSearcher.search(query, 2);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document doc = indexSearcher.doc(docId);
            String id = doc.get("id");
            System.out.println(id);
            String name = doc.get("name");
            System.out.println(name);
        }
        indexReader.close();
    }

    /**
     * 根据关键字删除某个文档对象
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        // 1.创建分词器对象
        Analyzer analyzer = new IKAnalyzer();
        // 2.指定索引库路径
        FSDirectory directory = FSDirectory.open(new File("D:\\luceneData"));
        // 3.创建indexWriter对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        Term term = new Term("id", "2");
        indexWriter.deleteDocuments(term);
        indexWriter.commit();
        indexWriter.close();
    }

    /**
     * 添加文档对象到索引库中
     * @throws Exception
     */
    @Test
    public void addDocument() throws Exception {
        // 1.创建分词对象
        Analyzer analyzer = new IKAnalyzer();
        // 2.指定索引库路径
        FSDirectory directory =FSDirectory.open(new File("D:\\luceneData"));
        // 3.创建indexWriter对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        Document document = new Document();
        TextField idField = new TextField("id", "123", Field.Store.YES);
        TextField nameField = new TextField("name", "测试数据", Field.Store.YES);
        TextField pic = new TextField("pic", "图片路径", Field.Store.YES);
        document.add(idField);
        document.add(nameField);
        document.add(pic);
        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();
    }

    /**
     * 修改索引库中的文档对象
     */
    @Test
    public void update() throws Exception {
        // 1.创建分词对象
        Analyzer analyzer = new IKAnalyzer();
        // 2.指定索引库位置
        FSDirectory directory = FSDirectory.open(new File("D:\\luceneData"));
        // 3.创建indexWriter对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        Term term = new Term("id", "3");
        Document document = new Document();
        TextField idField = new TextField("id", "7" , Field.Store.YES);
        TextField nameField = new TextField("name", "测试文档更新", Field.Store.YES);
        TextField picField = new TextField("pic", "图片地址", Field.Store.YES);
        document.add(idField);
        document.add(nameField);
        document.add(picField);
        indexWriter.updateDocument(term, document);
        indexWriter.commit();
        indexWriter.close();
    }
}
