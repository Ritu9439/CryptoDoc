package proj.dummyexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import proj.dummyexample.Adapter.NewsAdapter;
import proj.dummyexample.GetterSetter.NewsData;

public class NewsActivity extends AppCompatActivity {

String url="https://letstalkbitcoin.com/rss/feed/blog?limit=15";
    RecyclerView newslist;
    ArrayList<NewsData> arrayList=new ArrayList<>();
    NewsAdapter newsAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        progressdialog=new ProgressDialog(NewsActivity.this);
        newslist= (RecyclerView) findViewById(R.id.newslist);
        layoutManager=new LinearLayoutManager(NewsActivity.this);
        newslist.setLayoutManager(layoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
        new MyNewsAsync().execute();
    }

    private class MyNewsAsync extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
                Document document=documentBuilder.parse(url);
                document.normalize();
                NodeList nodeList=document.getElementsByTagName("item");
                for (int i=0;i<nodeList.getLength();i++)
                {
                    Node node=nodeList.item(i);
                    Element element= (Element) node;

                    String title=getDOMdata(element,"title");
                    String pubDate=getDOMdata(element,"pubDate");
                    String description=getDOMdata(element,"description");
                    String link=getDOMdata(element,"link");
                    NewsData newsdata=new NewsData("",title,pubDate,"",description,link);
                    arrayList.add(newsdata);
                }



            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        private String getDOMdata(Element element, String title) {
            NodeList nolist=element.getElementsByTagName(title);
            Node child=nolist.item(0);
            Element ele= (Element) child;
            String data=ele.getTextContent();
            return data;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressdialog.isShowing()){
                progressdialog.dismiss();
            }

            newsAdapter=new NewsAdapter(arrayList,NewsActivity.this);
            newslist.setAdapter(newsAdapter);
        }
    }
}
