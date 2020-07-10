package com.listgrid.demo.bean;

import com.listgrid.demo.R;

public class Model {

    // 第一个listview的文本数据数组
    public static String[] LISTVIEWTXT = new String[] { "VIP", "电影", "电视剧", "综艺", "少儿",
            "电竞", "新闻", "美食", "购物", "运动健身", "丽人", "酒店"};

    // 第二个listview的文本数据
    public static String[][] MORELISTTXT = {
            { "正在热播", "猜你喜欢", "最近更新", "最受好评", "热播影视", "精彩综艺"},
            { "全部美食", "小吃快餐", "西餐火锅", "川菜粤菜", "日本寿司", "面包甜点",
                    "韩国料理", "东南亚菜", "湘菜鲁菜", "素菜海鲜", "其他菜系" },
            { "休闲购物", "综合商场", "服饰鞋包", "超市便利", "特色集市", "品牌折扣", "珠宝饰品", "运动户外",
                    "食品茶酒", "书店药店", "数码产品", "京味购物", "亲子购物", "家具建材", "更多购物" },
            { "休闲娱乐", "景点郊游", "电影酒吧", "公园温泉", "文化艺术", "足疗按摩", "洗浴茶馆",
                    "游乐游艺", "桌面游戏", "DIY手工", "休闲网吧", "真人CS", "棋牌室", "私人影院", "更多娱乐" },
            { "运动健身", "健身中心", "游泳健身", "瑜伽舞蹈", "羽毛球馆", "足球台球", "体育场馆",
                    "高尔夫场", "武术场馆", "网球篮球", "保龄球馆", "乒乓球馆",
                    "更多体育" },
            { "全部丽人","美容美发", "美甲化妆", "瑜伽舞蹈", "瘦身纤体", "个性写真", "齿科整形" },
            { "全部结婚", "婚纱摄影", "婚宴酒店", "婚纱礼服", "婚庆公司", "婚戒首饰", "个性写真", "彩妆造型",
                    "婚礼小礼品", "婚礼跟拍", "婚车租赁", "司仪主持", "婚房装修", "更多服务" },
            { "全部酒店", "经济酒店", "五星酒店", "四星酒店", "三星酒店", "农家度假",
                    "公寓酒店", "青年旅社", "精品酒店", "更多酒店" },
            { "全部爱车", "维修保养", "汽车销售", "停车加油", "配件车饰", "汽车租赁", "汽车保险" },
            { "全部亲子", "亲子摄影", "幼儿教育", "亲子游乐", "孕产护理", "亲子购物", "更多服务" },
            { "生活服务", "医院银行", "齿科宠物", "快照冲印", "学校商务", "旅行培训",
                    "购物网站", "干洗家政", "护理小区", "更多服务" },
            { "全部家装", "家具家装", "家用电器", "家装卖场", "装修设计" } };

    public static final int[] imgList1 = {R.mipmap.movie1, R.mipmap.movie2, R.mipmap.movie3, R.mipmap.movie4,
            R.mipmap.movie4, R.mipmap.movie2, R.mipmap.movie3, R.mipmap.movie2,
            R.mipmap.movie1, R.mipmap.movie2, R.mipmap.movie1, R.mipmap.movie2,
            R.mipmap.movie3, R.mipmap.movie2, R.mipmap.movie1, R.mipmap.movie4,
            R.mipmap.movie4, R.mipmap.movie1, R.mipmap.movie1, R.mipmap.movie3};

    public static final int[] imgList2 = {R.mipmap.movie8, R.mipmap.movie7, R.mipmap.movie6, R.mipmap.movie5,
            R.mipmap.movie5, R.mipmap.movie7, R.mipmap.movie8, R.mipmap.movie6,
            R.mipmap.movie6, R.mipmap.movie7, R.mipmap.movie8, R.mipmap.movie5,
            R.mipmap.movie7, R.mipmap.movie5, R.mipmap.movie6, R.mipmap.movie8,
            R.mipmap.movie7, R.mipmap.movie7, R.mipmap.movie8, R.mipmap.movie5};

    public static String[] nameList1 = new String[] { "蜘蛛侠：平行宇宙", "独家记忆", "蜘蛛侠：平行宇宙", "独家记忆",
            "蜘蛛侠：平行宇宙", "独家记忆", "蜘蛛侠：平行宇宙", "独家记忆",
            "蜘蛛侠：平行宇宙", "独家记忆", "蜘蛛侠：平行宇宙", "独家记忆",
            "蜘蛛侠：平行宇宙", "独家记忆", "蜘蛛侠：平行宇宙", "独家记忆",
            "蜘蛛侠：平行宇宙", "独家记忆", "蜘蛛侠：平行宇宙", "独家记忆"};

    public static String[] nameList2 = new String[] { "波希米亚狂想曲", "神奇动物：格林德沃之罪", "波希米亚狂想曲", "神奇动物：格林德沃之罪",
            "波希米亚狂想曲", "神奇动物：格林德沃之罪", "波希米亚狂想曲", "神奇动物：格林德沃之罪",
            "波希米亚狂想曲", "神奇动物：格林德沃之罪", "波希米亚狂想曲", "神奇动物：格林德沃之罪",
            "波希米亚狂想曲", "神奇动物：格林德沃之罪", "波希米亚狂想曲", "神奇动物：格林德沃之罪",
            "波希米亚狂想曲", "神奇动物：格林德沃之罪", "波希米亚狂想曲", "神奇动物：格林德沃之罪"};
}
