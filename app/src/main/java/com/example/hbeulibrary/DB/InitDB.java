package com.example.hbeulibrary.DB;

import com.example.hbeulibrary.R;
import org.litepal.LitePal;

public class InitDB {

    public static void initDB(){
        //调用LitePal的getDatabase()方法，数据库自动创建
        LitePal.getDatabase();
        Book book1 = new Book();
        book1.setBookImageId(R.drawable.yuyanc);
        book1.setBookName("C语言程序设计");
        book1.setBookAuthor("谭浩强");
        book1.setBookPublish("清华大学出版社");
        book1.setBookPublishTime("2010-6-1");
        book1.setBookIBSN("9787302224464");
        book1.setPages(390);
        book1.setPrice(33.00);
        book1.setLayout("平装");
        book1.setNumber(2);
        book1.setInfo("《C程序设计(第4版)》按照C语言的新标准C99进行介绍，所有程序都符合C99的规定，" +
                "使编写程序更加规范；对C语言和程序设计的基本概念和要点讲解透彻，全面而深入；" +
                "按照作者提出的“提出问题―解决问题―归纳分析”三部曲进行教学、组织教材；" +
                "《C程序设计(第4版)》的每个例题都按以下几个步骤展开:提出任务―解题思路―编写程序―" +
                "运行程序―程序分析―有关说明。符合读者认知规律，容易入门与提高。");
        book1.save();
        Book book2 = new Book();
        book2.setBookImageId(R.drawable.baiyexing);
        book2.setBookName("白夜行");
        book2.setBookAuthor("[日]东野圭吾");
        book2.setBookPublish("南海出版公司");
        book2.setBookPublishTime("2015-10-2");
        book2.setBookIBSN("9787544242516");
        book2.setPages(467);
        book2.setPrice(29.80);
        book2.setLayout("平装");
        book2.setNumber(1);
        book2.setInfo("只希望能手牵手在太阳下散步”，这个象征故事内核的绝望念想，" +
                "有如一个美丽的幌子，随着无数凌乱、压抑、悲凉的故事片段像纪录片一样一一还原：" +
                "没有痴痴相思，没有海枯石烂，只剩下一个冰冷绝望的诡计，最后一丝温情也被完全抛弃，" +
                "万千读者在一曲救赎罪恶的凄苦爱情中悲切动容……");
        book2.save();
        Book book3 = new Book();
        book3.setBookImageId(R.drawable.langchaozhidian);
        book3.setBookName("浪潮之巅");
        book3.setBookAuthor("[美]吴军");
        book3.setBookPublish("电子工业出版社");
        book3.setBookPublishTime("2011-8");
        book3.setBookIBSN("9787121139512");
        book3.setPages(584);
        book3.setPrice(55.00);
        book3.setLayout("平装");
        book3.setNumber(0);
        book3.setInfo("近一百多年来，总有一些公司很幸运地、有意识或无意识地站在技术革命的浪尖之上。" +
                "在这十几年间，它们代表着科技的浪潮，直到下一波浪潮的来临......");
        book3.save();
        Book book4 = new Book();
        book4.setBookImageId(R.drawable.wanli15nianjpg);
        book4.setBookName("万历15年");
        book4.setBookAuthor("[美]黄仁宇");
        book4.setBookPublish("生活·读书·新知三联书店");
        book4.setBookPublishTime("1997-5");
        book4.setBookIBSN("9787108009821");
        book4.setPages(320);
        book4.setPrice(18.00);
        book4.setLayout("平装");
        book4.setNumber(1);
        book4.setInfo("万历十五年，亦即公元1587年，在西欧历史上为西班牙舰队全部出动征英的前一年；" +
                "而在中国，这平平淡淡的一年中，发生了若干为历史学家所易于忽视的事件。这些事件，" +
                "表面看来虽似末端小节，但实质上却是以前发生大事的症结，也是将在以后掀起波澜的机缘。" +
                "在历史学家黄仁宇的眼中，其间的关系因果，恰为历史的重点，而我们的大历史之旅，也自此开始....");
        book4.save();
        Book book5 = new Book();
        book5.setBookImageId(R.drawable.santi);
        book5.setBookName("三体-地球往事");
        book5.setBookAuthor("刘慈欣");
        book5.setBookPublish("重庆出版社");
        book5.setBookPublishTime("2008-1");
        book5.setBookIBSN("9787536692930");
        book5.setPages(302);
        book5.setPrice(23.00);
        book5.setLayout("平装");
        book5.setNumber(1);
        book5.setInfo("文化大革命如火如荼进行的同时。军方探寻外星文明的绝秘计划“红岸工程”取得了突破性进展。" +
                "但在按下发射键的那一刻，历经劫难的叶文洁没有意识到，她彻底改变了人类的命运。" +
                "地球文明向宇宙发出的第一声啼鸣，以太阳为中心，以光速向宇宙深处飞驰....");
        book5.save();
        Book book6 = new Book();
        book6.setBookImageId(R.drawable.xiaoshenkedejiushu);
        book6.setBookName("肖申克的救赎");
        book6.setBookAuthor("[美]斯蒂芬·金");
        book6.setBookPublish("人民文学出版社");
        book6.setBookPublishTime("2006-7");
        book6.setBookIBSN("9787020054985");
        book6.setPages(466);
        book6.setPrice(29.90);
        book6.setLayout("平装");
        book6.setNumber(1);
        book6.setInfo("这本书收入斯蒂芬·金的四部中篇小说，是他作品中的杰出代表作。" +
                "其英文版一经推出，即登上《纽约时报》畅销书排行榜的冠军之位，" +
                "当年在美国狂销二十八万册。目前，这本书已经被翻译成三十一种语言，" +
                "同时创下了收录的四篇小说中有三篇被改编成轰动一时的电影的记录。");
        book6.save();
        Book book7 = new Book();
        book7.setBookImageId(R.drawable.jobs);
        book7.setBookName("史蒂夫·乔布斯传");
        book7.setBookAuthor("[美]沃尔特·艾萨克森");
        book7.setBookPublish("中信出版社");
        book7.setBookPublishTime("2011-10-24");
        book7.setBookIBSN("9787508630069");
        book7.setPages(560);
        book7.setPrice(68.00);
        book7.setLayout("平装");
        book7.setNumber(1);
        book7.setInfo("这本乔布斯唯一授权的官方传记，在2011年上半年由美国出版商西蒙舒斯特对外发布出版消息以来，" +
                "备受全球媒体和业界瞩目，这本书的全球出版日期最终确定为2011年11月21日，简体中文版也将同步上市。");
        book7.save();
        Book book8 = new Book();
        book8.setBookImageId(R.drawable.java1);
        book8.setBookName("Java核心技术");
        book8.setBookAuthor(" [美]凯.S.霍斯特曼");
        book8.setBookPublish("机械工业出版社");
        book8.setBookPublishTime("2016-9");
        book8.setBookIBSN("9787111547426");
        book8.setPages(711);
        book8.setPrice(119.00);
        book8.setLayout("平装");
        book8.setNumber(1);
        book8.setInfo("Java领域最有影响力和价值的著作之一，由拥有20多年教学与研究经验的资深Java技术专家撰写(获Jolt大奖)，" +
                "与《Java编程思想》齐名，10余年全球畅销不衰，广受好评。第10版根据Java SE 8全面更新，同时修正了第9版中的不足，" +
                "系统全面讲解了Java语言的核 心概念、语法、重要特性和开发方法，包含大量案例，实践性强。");
        book8.save();
        Book book9 = new Book();
        book9.setBookImageId(R.drawable.java1);
        book9.setBookName("Java核心技术");
        book9.setBookAuthor("[美]凯.S.霍斯特曼");
        book9.setBookPublish("机械工业出版社");
        book9.setBookPublishTime("2016-9");
        book9.setBookIBSN("9787111547426");
        book9.setPages(711);
        book9.setPrice(119.00);
        book9.setLayout("平装");
        book9.setNumber(1);
        book9.setInfo("Java领域最有影响力和价值的著作之一，由拥有20多年教学与研究经验的资深Java技术专家撰写(获Jolt大奖)，" +
                "与《Java编程思想》齐名，10余年全球畅销不衰，广受好评。第10版根据Java SE 8全面更新，同时修正了第9版中的不足，" +
                "系统全面讲解了Java语言的核 心概念、语法、重要特性和开发方法，包含大量案例，实践性强。");
        book9.save();
        Book book10 = new Book();
        book10.setBookImageId(R.drawable.python1);
        book10.setBookName("Python编程");
        book10.setBookAuthor("[美]埃里克·马瑟斯");
        book10.setBookPublish("人民邮电出版社");
        book10.setBookPublishTime("2016-7-1");
        book10.setBookIBSN("9787115428028");
        book10.setPages(459);
        book10.setPrice(89.00);
        book10.setLayout("平装");
        book10.setNumber(1);
        book10.setInfo("本书是一本针对所有层次的Python 读者而作的Python 入门书。全书分两部分：" +
                "第一部分介绍用Python 编程所必须了解的基本概念，包括matplotlib、NumPy 和Pygal 等强大的Python " +
                "库和工具介绍，以及列表、字典、if 语句、类、文件与异常、代码测试等内容；第二部分将理论付诸实践，" +
                "讲解如何开发三个项目，包括简单的Python 2D 游戏开发如何利用数据生成交互式的信息图，" +
                "以及创建和定制简单的Web 应用，并帮读者解决常见编程问题和困惑。");
        book10.save();
        Book book11 = new Book();
        book11.setBookImageId(R.drawable.shujujiegou);
        book11.setBookName("数据结构(C语言版)");
        book11.setBookAuthor("严蔚敏/吴伟民");
        book11.setBookPublish("清华大学出版社");
        book11.setBookPublishTime("2012-5");
        book11.setBookIBSN("9781302023683");
        book11.setPages(335);
        book11.setPrice(29.00);
        book11.setLayout("平装");
        book11.setNumber(1);
        book11.setInfo("《数据结构》（C语言版）是为“数据结构”课程编写的教材，也可作为学习数据结构及其算法的C程序设计" +
                "的参数教材。本书的前半部分从抽象数据类型的角度讨论各种基本类型的数据结构及其应用；" +
                "后半部分主要讨论查找和排序的各种实现方法及其综合分析比较。");
        book11.save();
        Book book12 = new Book();
        book12.setBookImageId(R.drawable.bainiangudu);
        book12.setBookName("百年孤独");
        book12.setBookAuthor("[哥伦比亚]加西亚·马尔克斯");
        book12.setBookPublish("南海出版公司");
        book12.setBookPublishTime("2011-6");
        book12.setBookIBSN("9787544253994");
        book12.setPages(360);
        book12.setPrice(39.50);
        book12.setLayout("精装");
        book12.setNumber(1);
        book12.setInfo("《百年孤独》是魔幻现实主义文学的代表作，描写了布恩迪亚家族七代人的传奇故事，" +
                "以及加勒比海沿岸小镇马孔多的百年兴衰，反映了拉丁美洲一个世纪以来风云变幻的历史。" +
                "作品融入神话传说、民间故事、宗教典故等神秘因素，巧妙地糅合了现实与虚幻，" +
                "展现出一个瑰丽的想象世界，成为20世纪最重要的经典文学巨著之一。" +
                "1982年加西亚•马尔克斯获得诺贝尔文学奖，奠定世界级文学大师的地位，" +
                "很大程度上乃是凭借《百年孤独》的巨大影响。");
        book12.save();
        Book book13 = new Book();
        book13.setBookImageId(R.drawable.daguodacheng);
        book13.setBookName("大国大城");
        book13.setBookAuthor("陆铭");
        book13.setBookPublish("上海人民出版社");
        book13.setBookPublishTime("2016-7-1");
        book13.setBookIBSN("9787208138636");
        book13.setPages(324);
        book13.setPrice(42.00);
        book13.setLayout("平装");
        book13.setNumber(1);
        book13.setInfo("房价陡升、雾霾遮天、交通拥堵；空巢老人、留守儿童、农民工的窘境.....中国的城市化进程刚刚过半，" +
                "但大城市的病状和乡村的隐痛已经成为人们关注的焦点。限制大城市人口流入，让农民工返乡，问题就能解决吗？不！去往城市，" +
                "来谈乡愁。社会经济学家陆铭比较全球经验，立足本土现状，基于实证，力倡中国发展大城市的重要性。社会问题宜疏不宜堵，" +
                "只有让市场本身充分发挥对包括劳动力在内的生产要素的调节作用，才能从根本上解决当下棘手的社会问题。政府的功能不是与市场博弈，" +
                "而是在市场失语的地方，以长远眼光，布局科学的基础设施、提供公共服务供给。《大国大城》将告诉你，" +
                "只有在聚集中经济发展才能走向均衡，地理的因素不容忽视，只有以追求人均GDP的均衡取代追求区域GDP的均衡，" +
                "才能充分发挥出大国的国家竞争力，最终提升全体人民的公共利益。");
        book13.save();

        User user1 = new User();
        user1.setAccount("admin");
        user1.setPassword("admin");
        user1.save();
    }

}
