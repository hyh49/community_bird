package com.example.new_community_bird;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment2 extends Fragment implements AbsListView.OnScrollListener {
    View thisview;//fragment的视图

    public View loadmoreView;
    private ListView listView;
    public LayoutInflater inflaters;
    public int last_index;
    public int total_index;
    public List<String> firstList = new ArrayList<String>();//表示首次加载的list
    public List<String> nextList = new ArrayList<String>();//表示出现刷新之后需要显示的lis
    public List<String> firstList_url = new ArrayList<String>();//表示首次加载的list_url
    public List<String> nextList_url = new ArrayList<String>();//表示出现刷新之后需要显示的lis_url

    public boolean isLoading = false;//表示是否正处于加载状态
    public ListViewAdapter adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TEXT = "param1";

    // TODO: Rename and change types of parameters
    private String mTextString;
    View rootView;

    public BlankFragment2() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BlankFragment2 newInstance(String param1) {
        BlankFragment2 fragment2 = new BlankFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, param1);

        fragment2.setArguments(args);
        return fragment2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTextString = getArguments().getString(ARG_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        thisview=inflater.inflate(R.layout.fragment_blank2, container, false);
        initView();
        initData();
        initList(10, 10);
        addListener();
        return thisview;
    }

    private void initView(){
        listView = (ListView) thisview.findViewById(R.id.listview_eat);
        inflaters = LayoutInflater.from(getActivity());
        loadmoreView = inflaters.inflate(R.layout.load_more, null);//获得刷新视图
        loadmoreView.setVisibility(View.VISIBLE);//设置刷新视图默认情况下是不可见的
    }
    private void initData() {
        listView.setOnScrollListener(this);
        listView.addFooterView(loadmoreView,null,false);
        adapter = new ListViewAdapter(getActivity(), firstList);
        listView.setAdapter(adapter);

    }
    private void addListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getActivity(), webview.class);
                it.putExtra("jurl", nextList_url.get(i));
                startActivity(it);
            }
        });
    }
    //获取吃文章
    public void eat_click() {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    FormBody.Builder params =new FormBody.Builder();
                    params.add("id","1");//用户的id
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://192.168.0.104:8888/login/eat")
                            .post(params.build())
                            .build(); //创造请求
                    Response response=client.newCall(request).execute();//获取后端返回值
                    String responseData=response.body().string();
                    JSONArray jsonArray=new JSONArray(responseData);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        nextList.add(jsonObject.getString("title"));
                        nextList_url.add(jsonObject.getString("link"));
                        Log.d("title",""+jsonObject.getString("title"));
                        Log.d("link",""+jsonObject.getString("link"));
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),"发生成功",Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),"网络连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
    public void initList(int firstCount,int nextCount)
    {
        firstList.add("老年人心血管病预防保健要点");
        firstList.add("老年人高血压病的特点及治疗措施");
        firstList.add("中老年人心血管病知识");
        firstList.add("老年人高血压病的护理");
        firstList.add("老年慢性病患者的睡眠质量与基础生理指标及情绪,认知的相关性研究");
        nextList.add("老年人心血管病预防保健要点");
        nextList.add("老年人高血压病的特点及治疗措施");
        nextList.add("中老年人心血管病知识");
        nextList.add("老年人高血压病的护理");
        nextList.add("老年慢性病患者的睡眠质量与基础生理指标及情绪,认知的相关性研究");
        nextList_url.add("http://xueshu.baidu.com/usercenter/paper/show?paperid=7a49b8f1ce3fb6a22c190658c1bcbc34&site=xueshu_se");
        nextList_url.add("http://xueshu.baidu.com/usercenter/paper/show?paperid=5249a5ff51cd72223f9dcce307735f5c&site=xueshu_se");
        nextList_url.add("http://xueshu.baidu.com/usercenter/paper/show?paperid=f781b3d078b53c4ebfc77c1891a966ba&site=xueshu_se");
        nextList_url.add("http://xueshu.baidu.com/usercenter/paper/show?paperid=9372fd1e985ff1dbb4dfc3721680ec06&site=xueshu_se");
        nextList_url.add("http://xueshu.baidu.com/usercenter/paper/show?paperid=12ee2f43bc12987f7f9962dfa37cc392&site=xueshu_se");


//        eat_click();

    }
//        @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        last_index = firstVisibleItem+visibleItemCount;
        total_index = totalItemCount;
        System.out.println("last:  "+last_index);
        System.out.println("total:  "+total_index);
    }

//        @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(last_index == total_index && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE))
        {
            //表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
            if(!isLoading)
            {
                //不处于加载状态的话对其进行加载
                isLoading = true;
                //设置刷新界面可见
                loadmoreView.setVisibility(View.VISIBLE);

                onLoad();


            }
        }
    }

    /**
     * 刷新加载
     */
    public void onLoad()
    {
        try {
            //模拟耗时操作5ms
            eat_click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(adapter == null)
        {
            adapter = new ListViewAdapter(getActivity(), firstList);
            listView.setAdapter(adapter);
        }else
        {
            adapter.updateView(nextList);
        }
        loadComplete();//刷新结束



    }

    /**
     * 加载完成
     */
    public void loadComplete()
    {
        loadmoreView.setVisibility(View.GONE);//设置刷新界面不可见
        isLoading = false;//设置正在刷新标志位false
        getActivity().invalidateOptionsMenu();
        listView.removeFooterView(loadmoreView);//如果是最后一页的话，则将其从ListView中移出
    }



}