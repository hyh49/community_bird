package com.example.new_community_bird;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment implements AbsListView.OnScrollListener {
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



    //以下为实现轮播的变量声明
    private static ViewPager viewPageral;
    private RadioGroup group;
    //图片资源，实际项目需要从网络获取
    private int[] imageIds = {R.drawable.photo_r_one, R.drawable.photo_r_two, R.drawable.photo_r_three};
    //存放图片的数组
    private List<ImageView> mList;
    //当前索引位置以及上一个索引位置
    private static int index = 0, preIndex = 0;
    //是否需要轮播标志
    private boolean isContinue = true;
    //定时器，用于实现轮播
    private Timer timer = new Timer();
    private MyHandler mHandler;

    View thisview;

    //以上
    //handle更新ui机制用以进行轮播
    public static class MyHandler extends Handler {
        private WeakReference<zhixun_activity> weakReference;

        public MyHandler(zhixun_activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() != null) {
                index++;
                viewPageral.setCurrentItem(index);
            }
            super.handleMessage(msg);
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TEXT = "param1";

    // TODO: Rename and change types of parameters
    private String mTextString;
    View rootView;

    public BlankFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, param1);

        fragment.setArguments(args);
        return fragment;
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
        thisview=inflater.inflate(R.layout.fragment_blank, container, false);
//        readFile(data, data1);


        initView();
        initData();
        initList(10, 10);
        addListener();
        initRadioButton(imageIds.length);//注意这句和上面那句顺序不能写反，否则会出现第一个圆点无法显示选中状态
        startSwitch();

            return thisview;

    }
    private void initView(){
//        View account = View.inflate(getActivity(), R.layout.fragment_blank, null);

        viewPageral = (ViewPager)thisview.findViewById(R.id.viewpagerl);
        listView = (ListView) thisview.findViewById(R.id.listview);
        group = thisview.findViewById(R.id.group);
        inflaters = LayoutInflater.from(getActivity());
        loadmoreView = inflaters.inflate(R.layout.load_more, null);//获得刷新视图
        loadmoreView.setVisibility(View.VISIBLE);//设置刷新视图默认情况下是不可见的

//        TextView textView=rootView.findViewById(R.id.healtha);
//        textView.setText(mTextString);
    }
    private void initData() {
        mList = new ArrayList<>();
        //新建并配置ArrayAapeter，用来显示图片轮播
        viewPageral.setAdapter(pagerAdapter);
        mHandler = new MyHandler((zhixun_activity) getActivity());
        listView.setOnScrollListener(this);
        listView.addFooterView(loadmoreView,null,false);
        adapter = new ListViewAdapter(getActivity(), firstList);
        listView.setAdapter(adapter);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.array_adapter, R.id.TextView, data);
//
//        listView.setAdapter(adapter);
    }
    private void addListener() {
        viewPageral.addOnPageChangeListener(onPageChangeListener);
        viewPageral.setOnTouchListener(onTouchListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getActivity(), webview.class);
                it.putExtra("jurl", nextList_url.get(i));
                startActivity(it);
            }
        });
    }
    //获取文章
    public void health_click() {
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    FormBody.Builder params =new FormBody.Builder();
                    params.add("id","1");//用户的id
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://192.168.0.104:8888/login/health")
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
    /**
     * 初始化我们需要加载的数据
     * @param firstCount
     * @param nextCount
     */
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

//        for(int i = 0;i < firstCount;i++)
//        {
//            firstList.add("第"+(i+1)+"个开始加载");
//        }
//
//        for(int i = 0;i < firstCount;i++)
//        {
//            nextList.add("第"+(i+1)+"个开始加载");
//        }
//        for(int i = 0;i < nextCount;i++)
//        {
//            nextList.add("刷新之后第"+(i+1)+"个开始加载");
//        }
//        health_click();

    }
    //    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        last_index = firstVisibleItem+visibleItemCount;
        total_index = totalItemCount;
        System.out.println("last:  "+last_index);
        System.out.println("total:  "+total_index);
    }

    //    @Override
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
                health_click();
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
            Thread.sleep(5);
        } catch (InterruptedException e) {
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
    /**
     * 进行图片轮播
     */
    public void startSwitch() {
        //执行定时任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //首先判断是否需要轮播，是的话我们才发消息
                if (isContinue) {
                    if (imageIds.length != 1)//多于一张图片才轮播
                        mHandler.sendEmptyMessage(1);
                }
            }
        }, 3000, 3500);//延迟3秒，每隔3.5秒发一次消息;
    }

    /**
     * 根据图片个数初始化按钮
     *
     * @param length 图片所在集合长度
     */
    private void initRadioButton(int length) {
        for (int i = 0; i < length; i++) {
            ImageView imageview = new ImageView(getActivity());
            if (length == 1) {
                imageview.setVisibility(View.GONE);
                return;
            }
            imageview.setImageResource(R.drawable.rg_selector);//设置背景选择器
            imageview.setPadding(20, 0, 0, 0);//设置每个按钮之间的间距
            //将按钮依次添加到RadioGroup中
            group.addView(imageview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //默认选中第一个按钮，因为默认显示第一张图片
            group.getChildAt(0).setEnabled(false);
        }
    }

    /**
     * 根据当前触摸事件判断是否要轮播
     */
    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                //手指按下和划动的时候停止图片的轮播
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    isContinue = false;
                    break;
                default:
                    isContinue = true;
            }
            if (imageIds.length == 1) {
                return true;//1张图片不允许滑动
            }
            return false;//注意这里只能返回false,如果返回true，Dwon就会消费掉事件，MOVE无法获得事件，
            // 导致图片无法滑动
        }
    };
    /**
     * 根据当前选中的页面设置按钮的选中
     */
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            index = position;//当前位置赋值给索引
            setCurrentDot(index % imageIds.length);//因为只有四个按钮，所以我们在此要对长度区域，保证范围在0到4
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * 设置对应位置按钮的状态
     *
     * @param i 当前位置
     */
    private void setCurrentDot(int i) {
        if (group.getChildAt(i) != null) {
            group.getChildAt(i).setEnabled(false);//当前按钮选中,显示蓝色
        }
        if (group.getChildAt(preIndex) != null) {
            group.getChildAt(preIndex).setEnabled(true);//上一个取消选中。显示灰色
            preIndex = i;//当前位置变为上一个，继续下次轮播
        }
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            //返回一个比较大的值，目的是为了实现无限轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % imageIds.length;//因为position非常大，而我们需要的position不能大于图片集合长度
            //所以在此取余
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(imageIds[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            mList.add(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//         注意在此不要做任何操作，因为我们需要实现向左滑动，否则会产生IndexOutOfBoundsException
            container.removeView(mList.get(position));
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //页面销毁的时候取消定时器
        if (timer != null) {
            preIndex = 0;
            index = 0;
            timer.cancel();
        }
    }

}
//直接从文件中读取url和标题
//    public void readFile(String data[], String data1[]) {
//
//        InputStream input = getResources().openRawResource(R.raw.jiankang);
//        InputStreamReader isr = null;
//        try {
//            isr = new InputStreamReader(input, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        BufferedReader bfr = new BufferedReader(isr);
//        String line = "";
//        int co = 0;
//        int co1 = 0;
//        int k = 0;
//        while (true) {
//            try {
//                if (!((line = bfr.readLine()) != null)) break;
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//            // 一次读入一行数据
//            if (k % 2 == 0) {
//                data[co] = line;
//                co++;
//                k++;
//            } else {
//                data1[co1] = line;
//                co1++;
//                k++;
//            }
//
//        }
//    }
