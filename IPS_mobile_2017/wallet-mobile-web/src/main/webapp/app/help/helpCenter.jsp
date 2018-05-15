<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no">
    <title>融桥宝-帮助中心</title>
    <link type="text/css" rel="stylesheet" href="<c:url value='/static/h5/css/vjmian.css'/>">
    <script type="text/javascript" src="<c:url value='/static/h5/js/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/h5/js/vjmjs.js'/>"></script>
    <script type="text/javascript">
        $(function() {
            var $menu = $(".menu");
            $(".header").click(function() {
                var flag = $(this).next().is(":hidden");
                $menu.hide();
                if (flag) {
                    $(this).next().show(100);
                }
            });
        });
    </script>
    <style>
        .menu {
            display: none;
            background: #F2F2F2;
            width: 100%+18px;
            margin-left: -18px;
            padding: 23px 0 23px 0;
        }

        .menu li {
            color: #5D7A8D;
            margin: 0 40px 0 40px;
            font-size: 0.82em;
            line-height: 1.5em;
            letter-spacing: 0.1em;
        }

        .header {
            margin-bottom: 18px;
        }

        .PbulickZXa {
            padding-bottom: 0;
        }

        a,
        img,
        button,
        input,
        textarea,
        div {
            -webkit-tap-highlight-color: rgba(255, 255, 255, 0);
        }

        img{max-width:100%;}
    </style>
</head>

<body>
<ul class="accordion MarginTop10" id="accordion">

<%--<!-- 模块srart -->--%>
<%--<!-- 一级标题start -->--%>
<%--<article class="helparticcle">--%>
    <%--<p class="PublicBTP">账号管理</p>--%>
<%--</article>--%>
<%--<!-- 一级标题end -->--%>

<%--<article class="PublicArticle BorderTB3f2">--%>
    <%--<!-- 二级标题start -->--%>
    <%--<li>--%>
        <%--<div class="link">--%>
            <%--<i class="fa fa-chevron-down"></i> 什么是安全卡？--%>
        <%--</div>--%>
        <%--<!-- 答案start -->--%>
        <%--<ul class="submenu">--%>
            <%--<li>企业用户：--%>
            <%--</li>--%>
        <%--</ul>--%>
        <%--<!-- 答案end-->--%>
    <%--</li>--%>
    <%--<!-- 二级标题end -->--%>

<%--</article>--%>
<%--<!-- 模块end -->--%>






















<!-- 第一块 -->
<!-- 模块srart -->
<!-- 一级标题start -->
<article class="helparticcle">
    <p class="PublicBTP">账号管理</p>
</article>
<!-- 一级标题end -->

<article class="PublicArticle BorderTB3f2">
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 如何进行注册？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>在融桥宝微信公众号中点击“我的钱包”-“来投资”-“个人”，选择“注册”，根据系统提示完成注册流程。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>手机收不到验证码怎么办？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>1. 请检查手机是否安装短信拦截或过滤软件，取消拦截设置；
                <br>2. 请确认手机是否因信号问题、欠费、停机等原因无法接收短信；
                <br>3. 短信接收过程中可能会出现延迟，请耐心等待，短信验证码在2分钟内均有效；
                <br>4. 如收不到短信验证码，可尝试获取语音验证码。
                <br>5. 您还可以联系客服，寻求帮助（400-007-8655）。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>如何更换手机号？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>如需变更已绑定的经实名认证过的手机号，请拨打客服热线：400-007-8655。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 账号注册后可以注销么？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>由于在注册阶段所提供的信息均为个人真实有效信息，无法申请注销，但根据《互联网保密协议》融桥宝将会对您提供的个人信息进行保密。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 忘记登录密码怎么办？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>在登录页面中选择“忘记密码”，提供您的绑定手机号，收到验证码后，按提示进行操作。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 为什么要设定支付密码？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>为了将账户安全保障等级进一步提高，您在融桥宝中，每笔涉及资金交易的操作，均需要支付密码来进行验证。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 如何进行支付密码重置？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>在融桥宝微信公众号中点击“我的钱包”-“来投资”-“个人”-“安全设置”，选择“支付密码”选项，按页面提示完成密码重置操作。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>我的支付密码为什么会被锁定，如何解锁？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>您在进行资金交易时，连续输错3次支付密码，您的支付密码就会被锁定，届时将无法进行任何资金操作；点击解锁，系统将会跳转至解锁支付密码页面，您可按页面提示修改密码。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
</article>
<!-- 模块end -->


<!-- 第二块 -->

<!-- 模块srart -->
<!-- 一级标题start -->
<article class="helparticcle">
    <p class="PublicBTP">银行卡</p>
</article>
<!-- 一级标题end -->

<article class="PublicArticle BorderTB3f2">
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 什么是安全卡，绑定之后如何修改？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>安全卡是您初次向融桥宝充值时绑定的银行卡。该卡是您唯一可进行提现操作的银行卡。为保障您融桥宝账户中的资金安全，要求持卡人必须与您在融桥宝提交的个人信息完全一致，才可绑定成功。一旦绑定成功，不可更换，如因特殊原因需更换已绑定的安全卡，请拨打客服热线：400-007-8655。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 什么是充值卡？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>充值卡是您向融桥宝账户充值的银行卡，现支持银行多达15家以上，每个用户可绑定多张充值卡。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

</article>
<!-- 模块end -->



<!-- 第三块 -->
<!-- 模块srart -->
<!-- 一级标题start -->
<article class="helparticcle">
    <p class="PublicBTP">定期理财</p>
</article>
<!-- 一级标题end -->

<article class="PublicArticle BorderTB3f2">
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>“定期理财”中的理财产品，拥有什么优势？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>高收益：根据所投项目的不同，固定年化收益最高可达8%，新手专享年化收益率最高可达10%。
                <br>低风险：“定期理财”的所有产品均由众安保险提供全额保单，保障本息到期兑付。
                <br>低门槛：“定期理财”产品100元即可投资，投资当天计息，充值提现全免费。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 如何查询众安保险提供的全额保单？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>项目投资完成后将自动生成唯一的保险凭证号，保险凭证号也可以在投资项目详情页面查看，点击保险凭证号后，可在众安保险官网或拨打众安保险客服热线400-999-9595查询到相关保险信息。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 投资期限有多长，计息方式是怎样的？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>定期理财产品的投资期限有小于1个月、1个月、3个月、6个月和12个月等不同的期限，期限越长收益更高，您可以根据需要选投心仪的项目；您在“定期理财”版块中的所有投资均为当日计息。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 投资结束后如何还付本息？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>您将在项目到期日第二天收到回款，本息全额将转至融桥宝余额账户。如发生逾期，众安保险承诺在两个工作日内赔付全额本息，并赔付这两个工作日的利息。例：项目到期日为9月30日，如发生逾期，众安保险在两个工作日内进行赔付，10月1-7号为国家法定假日， 则您将在10月9号获得包含7个法定假日和2个工作日在内的，一共9个自然日的所有利息。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>投资项目有可能提前到期吗？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>有可能，以下两种情况会导致项目提前到期：
                <br>1、当借款人资产风险较高时，该借款项目会提前到期。此时，众安保险将在两个工作日内赔付投资人剩余未偿还的本金及当期利息。
                <br>2、借款人提前还款时，该借款项目会提前到期。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
</article>
<!-- 模块end -->





<!-- 第四块 -->
<!-- 模块srart -->
<!-- 一级标题start -->
<article class="helparticcle">
    <p class="PublicBTP">存钱罐</p>
</article>
<!-- 一级标题end -->

<article class="PublicArticle BorderTB3f2">
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 什么是“存钱罐”？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>“存钱罐”是融桥宝为用户提供的一款灵活的活期财富增值计划。资金转入后，即购买了由TCL集团（SZ.000100）提供的经严格风控筛选的金融产品；收益是银行活期存款的20倍，宝宝类产品的2倍；100元起投，随存随取，提现实时到账。是您闲散资金投资增值的不二之选！
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> “存钱罐”和其他活期产品相比有什么优势？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li><img src="<c:url value='/static/weixin_pic/help_center/advantage.jpg'/>" >
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> “存钱罐”的收益是如何计算的？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>
            “存钱罐”的收益每日（T日）核算，次日（T+1日）发放。用户在工作日（T日）24点之前发出申购请求，次工作日（T+1日）确认申购份额并计息，隔日（T+2日）发放收益，遇节假日顺延。
            <br><br>
            <div><img src="<c:url value='/static/weixin_pic/help_center/receive_calculate.jpg'/>" ></div>
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> “存钱罐”的购买规则？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>100元起投，且必须为整数。如：105元，2008元
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> “存钱罐”的资金提现时间？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>融桥宝“存钱罐”提现至银行卡7x24小时实时到账，是全网首家可支持超过2000家银行卡提现即时到账的平台，再也不必担心理财资金因无法及时提现到账而影响使用了。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>“存钱罐”提现是否有金额限制？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>“存钱罐”资金转出至银行卡单笔限额5万，单日限3笔；转出至“余额账户”无任何限制。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

</article>
<!-- 模块end -->


<!-- 第五块 -->
<!-- 模块srart -->
<!-- 一级标题start -->
<article class="helparticcle">
    <p class="PublicBTP">小金鱼</p>
</article>
<!-- 一级标题end -->

<article class="PublicArticle BorderTB3f2">
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>什么是“小金鱼”？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>“小金鱼”是融桥宝用户专享的投资借款，该借款真实有效，用户可用该笔借款投资融桥宝中的短期财富管理计划，并非普通的理财体验金可以比拟。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> “小金鱼”如何使用？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>当鱼池内有至少一条“小金鱼”时，您就可以用“小金鱼”投资我们为您准备的财富管理计划，最高收益达10%。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> “小金鱼”如何获得？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>注册绑卡即可获得一条“小金鱼”；完善个人社保公积金信息可再获一条“小金鱼”，每条“小金鱼”价值5000元。30个自然日内在定期“定期理财”累计投资金额超过十万元，每十万元，还可再获得“小金鱼”一条，且鱼池中的“小金鱼”没有累计上限。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> “小金鱼”可以兑换现金么？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>“小金鱼”无法兑换现金，仅可用于财富管理计划投资，但用“小金鱼”投资产生的所有收益归您所有。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 为什么我的申请未被通过？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>申请未通过，可能是由于您所提交的个人信息或银行卡信息有误，请仔细校对信息，以免耽误您的审批进度。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 如何还款？可以提前还款么？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>“小金鱼”不可以提前还款。在您所投资的财富管理计划到期后，系统将自动收回在该笔投资中的本金。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

</article>
<!-- 模块end -->






<!-- 第六块 -->
<!-- 模块srart -->
<!-- 一级标题start -->
<article class="helparticcle">
    <p class="PublicBTP">工资易贷</p>
</article>
<!-- 一级标题end -->

<article class="PublicArticle BorderTB3f2">
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>什么是工资易贷？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>工资易贷是一款由融桥宝专业金融团队打造的房产抵押短期借款服务。您可根据自身条件提供各类不动产抵押物（住宅、商铺、写字楼），满足不同额度的借款或资金周转需求。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>对申请人有什么基本要求？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>1、必须为房产权利人
<br>2、如已婚则需取得配偶的同意，配偶需签署《同意抵押承诺书》
<br>3、中国公民（不含港澳台人士），年满18周岁，不超过65周岁，身体健康并具有完全民事行为能力的自然人
<br>4、当前没有被法院强制执行信息
<br>5、不从事分控审批所认定的高危行业
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>房产抵押物的范围是怎样的？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>目前仅限上海地区，其他地区正在陆续开放中。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>业务流程是什么样的？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>当借款人提交申请后，我们会依次进行房价评估-客户面谈-审核-抵押放款。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>借款额度是多少？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>根据您所抵押房产的评估价值而定，单套房产10-1000万，多套房产（不超过5套）不超过2000万。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>借款期限是多久？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>借款期限为3个月、6个月及12个月。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>借款审核一般需要多长时间？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>资料提交完整后，融桥宝在当天审核完成，抵押回执后2小时内放款到账。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>我借款的成本大概是多少？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>一押：1.25%- 1.67%/月；二押：1.42%- 1.83%/月（具体费率以合同为准）
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>还款方式是哪一种？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>按月付息，到期还本。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>可以提前还款吗？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>可以。借款人在借款发放成功之日起至借款到期日前一个月（不含该日）之前提出全额提前还款的，除应支付本金及当期利息，您还需额外支付一个月的利息作为提前还款违约金；借款人在借款到期日前一个月内提出全额提前还款的，应支付本金及截至借款到期日的利息，此时无需支付提前还款违约金。例如借款人3月5号从平台借款100万元，期限6个月。如果其选择在8月5号之前还款，需额外支付一个月的利息作为提前还款违约金；如果其选择在8月5号到9月5号之间还款，其只需支付本金及截至借款到期日的利息即可。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i>如果产生逾期，会有罚息嘛？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>逾期是会有罚息的，如果是当期应还利息逾期，罚息=逾期当期利息*千分之二*逾期天数；如果是当期应还本息逾期，罚息=逾期当期本息*千分之二*逾期天数。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->
    
    

    

</article>
<!-- 模块end -->





<!-- 第七块 -->
<!-- 模块srart -->
<!-- 一级标题start -->
<article class="helparticcle">
    <p class="PublicBTP">充值/提现</p>
</article>
<!-- 一级标题end -->

<article class="PublicArticle BorderTB3f2">
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 如何进行充值/提现，是否收费？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>在融桥宝微信公众号中点击“我的钱包”-“来投资”-“个人”，就可看到页面中的“充值”、“提现”选项，按提示操作即可完成充值/提现；充值、提现不收取任何费用。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 充值是否有限额，是否有次数限制？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>
                融桥宝对于客户的充值没有金额和次数限制，但客户在充值过程中的单笔限额取决于您绑定的银行卡所属银行的充值限额。
                <br><br>
                <div><img src="<c:url value='/static/weixin_pic/help_center/bank_limit_amount.jpg'/>" ></div>
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 提现是否有限额及次数限制？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>账户余额提现单笔限额5万，单日限3笔。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 账户余额提现需多久到账？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>根据不同银行的响应速度，在工作日12:00之前发出账户余额提现申请，您的资金将会在1-2个工作日到账；在工作日12:00之后发出账户余额提现申请，您的资金将会在2-3个工作日到账，如遇节假日则相应顺延。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 提现中的冻结资金是指什么？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>冻结资金是指您申购“存钱罐”时，尚未确认产品份额的资金；或“提现”申请时，尚未完成提现流程的资金。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 充值支持信用卡吗？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>信用卡不支持充值。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 如何进行线下大额充值？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>您只需致电融桥宝VIP客服热线 400-007-8655，融桥宝VIP专员将为您提供专业的咨询服务。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 什么是账户余额？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li> 融桥宝中的余额既可用来购买活期理财“存钱罐”或定期理财“定期理财”，也可直接提现至银行卡。您在“定期理财”中的投资如到期，本息将自动转入余额账户。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 总资产如何统计？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>融桥宝账户总资产=账户余额+投资或提现中冻结金额+“存钱罐”中的本金及收益+“定期理财”中的投资本金。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 账单中有哪些信息？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>账单中可以查询到您融桥宝账户中每笔资金变动的明细。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

</article>
<!-- 模块end -->





<!-- 第七块 -->
<!-- 模块srart -->
<!-- 一级标题start -->
<article class="helparticcle">
    <p class="PublicBTP">安全保障</p>
</article>
<!-- 一级标题end -->

<article class="PublicArticle BorderTB3f2">
    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 为什么要进行实名认证？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>实名认证是为了确认您的身份以及确保您在融桥宝投资借款合同的有效性与合法性；并且您在融桥宝平台中的资金能且仅能提现到与实名认证信息一致的银行卡内，100%保障您的资金安全。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->


    

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 我的资金安全如何保障？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>融桥宝的用户将免费获得由中国人保财险提供的账户安全责任险。账户安全责任险保障平台资金因被他人盗用而导致的损失、手机丢失导致的资金损失以及平台账户因被他人盗用而导致的资金损失。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 电子合同是合法的吗？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>根据《合同法》和《电子签名法》的规定，当事人可以采用合同书、信件和数据电文（包括电报、电传、传真、电子数据交换和电子邮件）等形式订立合同，并通过以电子形式所含、所附用于识别签名人身份并表明签名人认可其中内容的数据电文等电子签名方式进行签署，当事人不能仅因合同采用电子签名、数据电文的形式就否定其法律效力。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 投资人和借款人的借贷关系是否合法？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>根据《合同法》第196条规定 “借款合同是借款人向贷款人借款，到期返还借款并支付利息的合同”，《合同法》允许自然人等普通民事主体之间发生借贷关系，并允许出借方到期可以收回本金和符合法律规定的利息。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

    <!-- 二级标题start -->
    <li>
        <div class="link">
            <i class="fa fa-chevron-down"></i> 融桥宝平台上借款人资质如何？
        </div>
        <!-- 答案start -->
        <ul class="submenu">
            <li>融桥宝慎重选择优质机构和个人进行合作，从源头上保证借款客户的质量。并有专业的风控团队对每一笔项目进行严格的审核筛选。
            </li>
        </ul>
        <!-- 答案end-->
    </li>
    <!-- 二级标题end -->

</article>
<!-- 模块end -->






<div class="cbdiv"></div>
</ul>
<div style="width:100%;height:60px;"></div>
<article class="PublicArticle BorderT3f2" style="position:fixed;bottom:0;width:100%;margin-bottom:0;">
    <a href="javascript:void" class="PbulickZXa PbulickZXas" style="margin-bottom:8px;border-bottom:0;">
        <img src="<c:url value='/static/h5/img/phoneimg.png'/>" style="float:left;width:27px;height:28px;margin-right:12px;">
        <p style="margin-left:0px;">拨打客服电话:${serviceHotline.phone_no}</p>
        <br><br>
        <pre style="float:left;width:27px; font-size:0.750em;margin-top:-6px;">${serviceHotline.service_time}</pre>
    </a>
    <div class="cboth"></div>
</article>
</body>
<!-- 百度统计代码 wallet-mobile -->
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        //hm.src = "//hm.baidu.com/hm.js\?b0334162ce58a358a7ea396016a5ddea";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>

</html>
