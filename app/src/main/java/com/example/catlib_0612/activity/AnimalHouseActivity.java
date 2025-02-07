package com.example.catlib_0612.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.catlib_0612.R;
import com.example.catlib_0612.adapter.AnimalHouseAdapter;
import com.example.catlib_0612.data.AninalHouse;

import java.util.ArrayList;
import java.util.List;

public class AnimalHouseActivity extends AppCompatActivity {
    private String TAG = "AnimalHouseActivity";
    private GridView gv_animalHouse_list;
    private AnimalHouseAdapter animalHouseAdapter;
    private List<AninalHouse> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_house);
        initView();
    }

    private void initView(){

        gv_animalHouse_list = (GridView) findViewById(R.id.gv_animalHouse_list);
        list = new ArrayList<>();
        setData();

        animalHouseAdapter = new AnimalHouseAdapter(getApplicationContext(),list);
        gv_animalHouse_list.setAdapter(animalHouseAdapter);
        gv_animalHouse_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"position"+i, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AnimalHouseInfoActivity.class);
                intent.putExtra("picture", list.get(i).getPicture());
                intent.putExtra("title", list.get(i).getTitle());
                intent.putExtra("phone", list.get(i).getPhone());
                intent.putExtra("address", list.get(i).getAddress());
                intent.putExtra("openTime", list.get(i).getOpenTime());
                intent.putExtra("note", list.get(i).getNote());
                intent.putExtra("lat", list.get(i).getLat());
                intent.putExtra("lng", list.get(i).getLng());
                startActivity(intent);
            }
        });
    }

    private void setData(){
        for (int i=0; i<1; i++){
            list.add(new AninalHouse(R.drawable.taipei_1,"臺北市動物之家","02-87913254 、02-87913255",
                    "臺北市內湖區安美街191號","週二至週日，上午10:00~12:30，下午1:30~4:00",getString(R.string.animal_H1_note),25.06063827326529, 121.60344455597328));
            list.add(new AninalHouse(R.drawable.taipei_2,"新北市板橋區公立動物之家","02-89662158",
                    "新北市板橋區板城路28-1號","週二至週日；上午10:00~12:00，下午14:00~16:00",getString(R.string.animal_H2_note),24.995585229518237, 121.44838332528855));
            list.add(new AninalHouse(R.drawable.taipei_3,"新北市新店區公立動物之家","02-22159462",
                    "新北市新店區安泰路235號","上午10:00~12:00，下午14:00~16:00",getString(R.string.animal_H3_note),24.927245789247824, 121.49079389645148));
            list.add(new AninalHouse(R.drawable.taipei_4,"新北市中和區公立動物之家","02-86685547",
                    "新北市中和區興南路三段100號","週二至週日；上午10:00~12:00，下午14:00~16:00",getString(R.string.animal_H4_note),24.975812588828965, 121.48870718111107));
            list.add(new AninalHouse(R.drawable.taipei_5,"新北市淡水區公立動物之家","02-26267558",
                    "新北市淡水區下圭柔山91-3號","週一至週五；上午10:00~12:00，下午14:00~16:00",getString(R.string.animal_H5_note),25.209994751548876, 121.43241722529343));
            list.add(new AninalHouse(R.drawable.taipei_6,"新北市瑞芳區公立動物之家","02-24063481",
                    "新北市瑞芳區靜安路四段(106縣道74.5K清潔隊場區內)","週一至週五；上午10:00~12:00，下午14:00~16:00",getString(R.string.animal_H6_note),25.077735934306453, 121.79767738794591));
            list.add(new AninalHouse(R.drawable.taipei_7,"新北市五股區公立動物之家","02-82925265",
                    "新北市五股區外寮路9-9號","週二至週日；上午10:00~12:00，下午14:00~16:00",getString(R.string.animal_H7_note),25.077895684287142, 121.41579929645508));
            list.add(new AninalHouse(R.drawable.taipei_8,"新北市八里區公立動物之家","02-26194428",
                    "新北市八里區長坑里6鄰長坑道路36號","週一至週五；上午10:00~12:00，下午14:00~16:00",getString(R.string.animal_H8_note),25.087855844501576, 121.39923375116058));
            list.add(new AninalHouse(R.drawable.taipei_9,"新北市三芝區公立動物之家","02-26365436",
                    "新北市三芝區青山路(龍巖人本旁)","週一至週五；上午10:00~12:00，下午14:00~16:00",getString(R.string.animal_H9_note),25.228925964731527, 121.53158111919161));
            list.add(new AninalHouse(R.drawable.taipei_10,"基隆市寵物銀行","02-24560148",
                    "基隆市七堵區大華三路45-12號(欣欣安樂園旁)","週二～週五下午13:00~16:00，週六上午9:00~12:00 中午休息時段：12:00~13:00 (國定例假日休息)",getString(R.string.animal_H10_note),25.127560680479025, 121.67466150698432));
            list.add(new AninalHouse(R.drawable.taipei_11,"高雄市壽山動物保護教育園區","07-5519059",
                    "高雄市鼓山區萬壽路350號","週二至週日，上午9：30~12：00，下午1：30~5：00。(週一及國定假日休園)",getString(R.string.animal_H11_note),22.636547005224088, 120.27479691174337));
            list.add(new AninalHouse(R.drawable.taipei_12,"高雄市燕巢動物保護關愛園區","07-6051002",
                    "高雄市燕巢區師大路98號","週二至週六，上午9:30~12:00，下午1:30-5:00(週一、週日及國定假日休園)",getString(R.string.animal_H12_note),22.7924412651531, 120.40485165222795));
            list.add(new AninalHouse(R.drawable.taipei_13,"臺中市動物之家南屯園區","04-23850976",
                    "臺中市南屯區中台路601號","每週一、二、四及六，下午1:30分至4:00 (最後入園時間為15:30)","",24.14392403581192, 120.58105026759841));
            list.add(new AninalHouse(R.drawable.taipei_14,"臺中市動物之家后里園區","04-25588024",
                    "臺中市后里區堤防路370號","每週一、二、四及六，下午1:30分至4:00 (最後入園時間為15:30)","",24.28656330640001, 120.70952298109539));
            list.add(new AninalHouse(R.drawable.taipei_15,"桃園市動物保護教育園區","03-4861760",
                    "327桃園市新屋區永興里藻礁路1668號","1. 開放參觀時間：每週二、四、五、六、日，上午09:30~下午16:00。(中午12時~13時，不開放入動物舍參觀) 。\n" +
                    "2. 尋找、拾獲、不擬飼養、犬貓屍體焚化委託服務及認領養業務：週一至週日，上午09:30~下午16:00。(中午12時~13時，不開放入動物舍參觀) 。\n" +
                    "3. 非開放日：每周一、三、國定連假及因天然災害經本市發布之停止上班日。","",25.007962222885645, 121.0282455964534));
            list.add(new AninalHouse(R.drawable.taipei_16,"新竹市動物保護教育園區","03-5368329",
                    "新竹市南寮里海濱路250號","週一至週五，上午10:00-12:00，下午14:00-16:00 ，週六上午10:00-12:00，週日、國定假日不開放。",getString(R.string.animal_H16_note),24.834089475701308, 120.92007005227242));
            list.add(new AninalHouse(R.drawable.taipei_17,"新竹縣動物保護教育園區","03-5519548",
                    "新竹縣竹北市縣政五街192號","週二至週五上午 9:00~11:30，下午13:00~16:00，週六(不含連假及節日)10:00~15:00",getString(R.string.animal_H17_note),24.828672495272208, 121.01505416761385));
            list.add(new AninalHouse(R.drawable.taipei_18,"苗栗縣動物保護教育園區","037-320049(苗栗縣動物保護防疫所)；037-558228(動物保護教育園區)",
                    "苗栗縣銅鑼鄉朝陽村6鄰朝北55-1號","週二至週六上午10:00-12:00、下午13:00-16:00\n" +
                    "（週日、週一、例假日、國定假日及連續假日無對外開放）",getString(R.string.animal_H18_note),24.499907263483376, 120.79437697684577));
            list.add(new AninalHouse(R.drawable.taipei_19,"南投縣公立動物收容所","049-2225440",
                    "南投縣南投市嶺興路36-1號","認養：週一至週五，上午9:00-11:30，下午1:30-4:00 及週六上午9:00-11:30\n" +
                    "參觀：週一至週日，上午9:00-11:30，下午1:30-4:00 (國定假日不開放認養)",getString(R.string.animal_H19_note),23.90447818376694, 120.67044603875792));
            list.add(new AninalHouse(R.drawable.taipei_20,"彰化縣流浪狗中途之家","(04)8590638",
                    "彰化縣員林市大峰里阿寶巷426號 (大門入口請由彰化縣芬園鄉大彰路一段875巷進入走到底)","週一至週四及週六，上午10:00~12:00，下午2:00~4:00。每逢週五、週日及國定假日，不對外開放。",getString(R.string.animal_H20_note),23.96937082552985, 120.61975255410098));
            list.add(new AninalHouse(R.drawable.taipei_21,"嘉義市動物保護教育園區","05-2168661",
                    "嘉義市彌陀路31號旁","週二至週六，上午9:00~11:30，下午2:00~4:30。","",23.464471245174252, 120.46881585408998));
            list.add(new AninalHouse(R.drawable.taipei_22,"嘉義縣動物保護教育園區","05-2721119",
                    "嘉義縣民雄鄉松山村後山仔37之2號","週二至週五：上午9：00至11：30、下午13：30~16：00，週六上午9：00至11：30，遇週日、一及國定假日休園，不對外開放。",getString(R.string.animal_H22_note),23.547930360160205, 120.50619312839319));
            list.add(new AninalHouse(R.drawable.taipei_23,"臺南市動物之家灣裡站","06-2964439",
                    "臺南市南區省躬里14鄰萬年路580巷92號","週二至週六上午9:00~12:00下午1:30~4:30；星期一、日及國定例假日不開放",getString(R.string.animal_H23_note),22.93693315159933, 120.1943419559267));
            list.add(new AninalHouse(R.drawable.taipei_24,"臺南市動物之家善化站","06-5832399",
                    "臺南市善化區昌隆里東勢寮1~19號","週二至週六，上午9:00~12:00，下午1:30~4:30；週一、日及國定例假日不開放。",getString(R.string.animal_H24_note),23.148906559351605, 120.33198081914551));
            list.add(new AninalHouse(R.drawable.taipei_25,"屏東縣公立犬貓中途之家","0900-493-311",
                    "屏東縣內埔鄉學府路1號(屏東科技大學內)","週一至週五(國定例假日休園，如有異動將公告於官方臉書 Hi毛寶貝-【屏東縣犬貓中途之家＆社團法人台灣愛狗人協會】)，上午10：00~12：00，下午1：00~4：30。",getString(R.string.animal_H25_note),22.643909019401956, 120.61083217921247));
            list.add(new AninalHouse(R.drawable.taipei_26,"宜蘭縣流浪動物中途之家","03-9602350 分機620",
                    "宜蘭縣五結鄉成興村利寶路60號","週一至週二及週四至週五上午 10:00-12:00，下午1:00-4:00\n" +
                    "（週三為舉行犬貓絕育活動，暫停開 放；另週六10:30-14:30為彈性開放，詳情開放日請洽宜蘭縣流浪 動物中途之家臉書。）","",24.666804424680254, 121.83090789644561));
            list.add(new AninalHouse(R.drawable.taipei_27,"花蓮縣狗貓躍動園區","(03)8421452",
                    "花蓮縣鳳林鎮林榮里永豐路255號","週一至週六，上午10:00~12:00 下午2:00~4:00(遇週日及國定假日停止開放)","",23.80068309038556, 121.47259614060349));
            list.add(new AninalHouse(R.drawable.taipei_28,"臺東縣動物收容中心","089-362011",
                    "臺東縣臺東市中華路4段999巷600號","周一至周日上午9:30~11:30 下午2:30~4:30","",22.72798956027571, 121.10372914058065));
            list.add(new AninalHouse(R.drawable.taipei_29,"澎湖縣流浪動物收容中心","06-9213559",
                    "澎湖縣馬公市烏崁里260號、261號","週二至週六，上午10:00至12:00，下午14:00至16:00","",23.555563847723338, 119.62754472471019));
            list.add(new AninalHouse(R.drawable.taipei_30,"金門縣動物收容中心","082-336625",
                    "金門縣金湖鎮裕民農莊20號","周一至周日，上午9:00~12:00，下午2:00~5:00開放參觀；週六、週日認領養採預約制。","",24.444240484068736, 118.44590727998137));
            list.add(new AninalHouse(R.drawable.taipei_31,"連江縣流浪犬收容中心","0836-25003",
                    "連江縣馬祖南竿鄉復興村223號","週一~週日，上午8:00~12:00，下午1:30~5:30。","",26.16116302280905, 119.94943114257725));

/*
            list.add(new AninalHouse(R.drawable.taipei_12,"高雄市燕巢動物保護關愛園區","07-6051002",
                    "高雄市燕巢區師大路98號","週二至週六，上午9:30~12:00，下午1:30-5:00(週一、週日及國定假日休園)",getString(R.string.animal_H12_note),));

*/
        }

    }
}