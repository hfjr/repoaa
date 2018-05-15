<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no">
    <title>融桥宝-帮助中心</title>
  		  <link type="text/css" rel="stylesheet" href="<c:url value='/static/h5/css/wjmain.css'/>">
		 	 <script type="text/javascript" src="<c:url value='/static/h5/js/jquery-1.8.2.min.js'/>"></script>

	      
	      <link type="text/css" rel="stylesheet" href="<c:url value='/static/h5/css/vjmian.css'/>">
	  	  <script type="text/javascript" src="<c:url value='/static/h5/js/jquery.min.js'/>"></script>
	  	  <script type="text/javascript" src="<c:url value='/static/h5/js/vjmjs.js'/>"></script>

<script type="text/javascript">
	$(function(){
		var $menu = $(".menu");
		$(".header").click(function(){
			var flag = $(this).next().is(":hidden");
			$menu.hide();
			if(flag){
				$(this).next().show(100);
			}
		});
	});
</script>


   <style>
       .menu {display:none;background:#F2F2F2;width:100%+18px;margin-left:-18px;padding:23px 0 23px 0;}
       .menu li {color:#5D7A8D;margin:0 40px 0 40px;font-size:0.82em;line-height:1.5em;letter-spacing:0.1em;}
       .header {margin-bottom:18px;}
       .PbulickZXa {padding-bottom:0;}
         a,img,button,input,textarea,div{-webkit-tap-highlight-color:rgba(255,255,255,0);}
   </style>
</head>
<body>

	 <ul class="accordion MarginTop10" id="accordion">
        <article class="helparticcle">
            <p class="PublicBTP">关于银行卡:</p>
        </article>
        <article class="PublicArticle BorderTB3f2">
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    什么是安全卡？
                </div>
                <ul class="submenu">
                    <li>企业用户：安全卡由您的企业为您绑定，也是您的原工资卡（支持超过2000家银行）；您在融桥宝中的所有资金（工资、余额）均可提现至该卡内，也是您唯一可以用于提现操作的银行卡，完全保留您的用卡习惯。
                        <br>个人用户：安全卡是您初次向融桥宝充值时绑定的第一张银行卡。该卡是您使用融桥宝服务时唯一可进行提现操作的银行卡。为保障您在融桥宝账户的资金安全，要求该卡持卡人必须与您所提供的个人信息完全一致，才可绑定成功；一旦绑定成功，不可更换。如需更换安全卡绑定，请致电客服。
                    </li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    什么是充值卡？
                </div>
                <ul class="submenu">
                    <li>充值卡是您可向融桥宝账户充值的银行卡，现支持15家银行（正在陆续增加中）。如您的安全卡不支持充值，可额外绑定充值卡进行充值，用户的充值卡的绑定数量没有限制（个人用户目前仅限一张充值卡）。
                    </li>
                </ul>
            </li>
        </article>
        <article class="helparticcle">
            <p class="PublicBTP">工资e账户：</p>
        </article>
        <article class="PublicArticle BorderTB3f2">
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    什么是工资e账户？
                </div>
                <ul class="submenu">
                    <li>工资e账户是融桥宝为您提供的通过优质货币基金进行余额增值的融桥宝子账户。如您是融桥宝“工资代发”用户，您每月的工资款将自动转入工资e账户，实现工资高效自动增值。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    工资e账户内的资金是否安全？
                </div>
                <ul class="submenu">
                    <li>工资e账户是由融桥宝合作银行为用户直接开立和监管的电子账户，用于代发工资及余额增值服务，具备银行系统的所有安全属性。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    工资e账户具有什么功能？
                </div>
                <ul class="submenu">
                    <li>工资e账户中的资金将通过银行系统自动申购收益较高的优质货币基金,从而实现您的工资智能增值，收益是银行活期存款的15-20倍。您可以随时将工资e账户中的资金瞬间提现至您绑定的工资卡中，与银行活期存款拥有同样的灵活性，全面超越常见的“宝宝类”理财产品。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    工资e账户中的资金提现时间？
                </div>
                <ul class="submenu">
                    <li>融桥宝已实现工资e账户资金提取至银行卡7x24小时实时到账，是全网首家实现2000家银行全部提现即时到账的平台，再也不必担心理财资金因无法及时提现而影响使用了。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    工资e账户中的资金提现是否有限制？
                </div>
                <ul class="submenu">
                    <li>工资e账户目前提现单笔限额5万，单日限3笔。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    工资e账户的收益是如何计算的？
                </div>
                <ul class="submenu">
                    <li>工资e账户的收益随货币基金的浮动收益率每日核算，次工作日发放。用户在工作日（T日）15点之前发出申购请求，T+1工作日确认申购份额并计息，T+2工作日发放收益，遇节假日顺延；在工作日的15点之后发出申购请求，则确认申购份额、计息和发放收益将顺延一个工作日。例：周四15:00（含15:00）至周五15:00申购，下周一确认申购份额并计息，周二发放收益。工资e账户的当日收益=（当日已确认份额的资金/10000 ）X 每万份收益。假设您已确认份额的资金为18000元，当天的每万份收益为1.25元，代入计算公式，您当日的收益为：2.25元。</li>               
                </ul>
            </li>
        </article>
        <article class="helparticcle">
            <p class="PublicBTP">V理财：</p>
        </article>
        <article class="PublicArticle BorderTB3f2">
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   融桥宝的V理财投资计划拥有什么优势？
                </div>
                <ul class="submenu">
                    <li>高收益：根据所投产品的不同，固定年化收益6.8%到9%不等，收益是银行活期的数十倍。10万元银行定存一年收益是2250元，在融桥宝您可以得到9000元；新晋用户可以获得新手奖励的机会，收益率最高可达10%。
						<br>低风险：V理财所有产品均由保险公司提供保障，项目到期，100%兑付本息。
						<br>低门槛：起投金额为100元。
					</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    投资期限有多长？
                </div>
                <ul class="submenu">
                    <li>通过V定期理财项目达成的交易按照投资期限划分，分为1个月、3个月、6个月和12个月及小于1个月5个不同的期限。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    投资限额是多少？
                </div>
                <ul class="submenu">
                    <li>100元起投，投资金额需为100的整数倍且不能超过该项目可投余额。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   计息方式是怎样的？
                </div>
                <ul class="submenu">
                    <li>用户在V理财中的投资项目均为当日计息，所投金额的当日收益即刻体现。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    投资结束后如何还付本息？
                </div>
                <ul class="submenu">
                    <li>您将在项目回款日当天24点前收到回款 。如发生逾期保险公司将在两个工作日内赔付全额本息，并赔付这两个工作日内所产生的利息 。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    项目到期后，如何赎回？
                </div>
                <ul class="submenu">
                    <li>项目到期后，在第二个工作日全额本息将会自动转入您的融桥宝账户余额。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    保险公司的理财安全险如何保障投资安全？
                </div>
                <ul class="submenu">
                    <li>理财安全险是保险公司为融桥宝用户提供的理财安全保障，是为了防范理财项目在到期后可能会产生的逾期还款行为，如项目借款人违约，保险公司将在2个工作日内进行全额本息赔付，并额外赔付两个工作日的相应利息。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   在法定假日前发生违约的项目，保险公司如何赔付？
                </div>
                <ul class="submenu">
                    <li>如在两个工作日内包含法定假日，则在法定假日结束后，您将获得包含法定假日在内的所有自然日利息 。
                                                                  例：项目到期日为9月30日，出现了逾期行为，保险公司本应在两个工作日内进行赔付，但10月1-7号为国家法定假日， 则您将在10月9号获得包含7个法定假日和2个工作日在内的，一共9个自然日的所有利息。
                    </li>
                </ul>
            </li>
            
                <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   如何证明保险生效且真实有效？
                </div>
                <ul class="submenu">
                    <li>投资项目满标后将自动生成唯一保险凭证号。投保成功后，保险公司会向投资人发出带有保险凭证号的提示短信，保险凭证号也可以在我的保障页面查看。
                    </li>
                </ul>
            </li>
            
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    投资项目有可能提前到期吗？
                </div>
                <ul class="submenu">
                    <li>有可能，以下两种情况会导致项目提前到期：
                        <br>1、当借款人资产风险较高时，该借款项目会提前到期。此时，保险公司将在两个工作日内赔付投资人剩余未偿还的本金及当期利息。
                        <br>2、借款人提前还款时，该借款项目会提前到期。
                     </li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    为何要进行实名认证升级？
                </div>
                <ul class="submenu">
                    <li>一旦系统提示您须进行实名认证升级，说明您在V理财中至少有一款产品出现了逾期还款；根据保险公司条例，您需要上传本人身份证正反面，方可及时获得赔付。</li>
                </ul>
            </li>
        </article>
        <article class="helparticcle">
            <p class="PublicBTP">充值/提现：</p>
        </article>
        <article class="PublicArticle BorderTB3f2">
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    如何充值？充值是实时到账么？
                </div>
                <ul class="submenu">
                    <li>在个人或理财产品界面中，选择充值选项，按提示操作即可完成充值，充值为实时到账。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    充值是否收取费用？
                </div>
                <ul class="submenu">
                    <li>向融桥宝账户充值无任何手续费用。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    账户余额如何提现？提现是否收费？
                </div>
                <ul class="submenu">
                    <li>点击财富页面右上角“充值/提现“图标，选择提现选项，即可进入提现流程。账户余额提现无手续费。</li>
                </ul>
            </li>
            
                <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                 账户余额提现需多久到账？
                </div>
                <ul class="submenu">
                    <li>用户在工作日12：00之前发出账户余额提现申请，融桥宝将会当天处理；根据不同银行的响应速度，您的资金将会在1-2个工作日到账，如遇节假日则相应顺延。</li>
                </ul>
            </li>
            
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    充值提现是否有限额？是否有次数限制？
                </div>
                <ul class="submenu">
                    <li>融桥宝对于用户的充值没有金额和次数限制，但用户在充值过程中的单笔限额取决于您所绑定银行卡所属银行的充值限额。如工商银行的单笔充值上限为5万元 。 账户余额提现每笔上限5万，每日提现的次数不得超过3笔。</li>
                </ul>
            </li>
            
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   提现后是否可以取消？
                </div>
                <ul class="submenu">
                    <li>账户余额提现申请不可取消，请您谨慎操作。</li>
                </ul>
            </li>
            
            
            
        </article>
        <article class="helparticcle">
            <p class="PublicBTP">安全保障：</p>
        </article>
        <article class="PublicArticle BorderTB3f2">
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   融桥宝平台是如何保障投资安全的？
                </div>
                <ul class="submenu">
                    <li>融桥宝平台上提供的两款产品，分别为自动对接货币基金的工资e账户（活期）和由保险公司提供100%本息保障的V理财（定期），均可保证不会出现投资风险。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   为什么要进行实名认证？
                </div>
                <ul class="submenu">
                    <li>实名认证是为了确认投资人的身份以及确保投资借款合同的有效性与合法性； 并且，客户在融桥宝平台中的资金能且仅能提现到至实名认证信息一致的银行卡内，100%保障资金安全；且只有通过实名认证升级后，才能获得保险公司提供的保险保障。</li>
                </ul>
            </li>
             <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                  我的账号被人盗了怎么办？
                </div>
                <ul class="submenu">
                    <li>您无需担心，您账户内资金能且仅能提现至您实名认证的银行卡内。如发现异常情况，请及时修改登录密码及交易密码并联系客服，如因账户被盗而导致资金损失，您将获得由中国人保财险所提供账户资金安全险的全额赔付（可点击财富页面“我的保障“板块领取）。</li>
                </ul>
            </li>
               <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                  保险公司提供哪些保障？
                </div>
                <ul class="submenu">
                    <li>融桥宝的用户将在注册和购买理财产品时，将分别获得由中国人保财险提供的账户资金安全险（可点击财富页面“我的保障“板块领取）和保险公司提供的理财安全险，最大限度保障您的资金安全。</li>
                </ul>
            </li>
        </article>
        <article class="helparticcle">
            <p class="PublicBTP">注册/登录：</p>
        </article>
        <article class="PublicArticle BorderTB3f2">
         <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   企业用户如何登陆？
                </div>
                <ul class="submenu">
                    <li>企业用户首次登录融桥宝无需进行注册环节，您的企业已为您成功进行开户及绑卡；您仅需选择登陆页面中的“快速激活“选项，按流程填写相应内容，即可完成登录。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    如何进行注册？
                </div>
                <ul class="submenu">
                    <li>首次安装“融桥宝”APP后，如非企业用户，在登录界面可以找到注册选项，在页面的操作提示下 ，完成注册流程即可。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    如何更换或解绑手机？
                </div>
                <ul class="submenu">
                    <li>如需变更实名认证过的绑定手机，需致电融桥宝的客服人员（客服电话：400 007 8655）</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    账号注册后可以注销么？
                </div>
                <ul class="submenu">
                    <li>由于在注册阶段所提供的信息均为个人真实有效信息，无法申请注销，但根据《互联网保密协议》融桥宝将会对您提供的个人信息进行保密。</li>
                </ul>
            </li>
        </article>
        
        <article class="helparticcle">
            <p class="PublicBTP">账户密码管理：</p>
        </article>
        <article class="PublicArticle BorderTB3f2">
<!--             <li> -->
<!--                 <div class="link"> -->
<!--                     <i class="fa fa-chevron-down"></i> -->
<!--                     支付密码是什么？ -->
<!--                 </div> -->
<!--                 <ul class="submenu"> -->
<!--                     <li>支付密码是为了保护您的账户安全，在提现时需要输入的6位密码，不同于登录密码。我们建议您在完成注册后第一时间进入“更多→账户中心→支付密码”设置您的支付密码。如您未进行设定，我们会在您第一次提现时，要求您进行设定。</li> -->
<!--                 </ul> -->
<!--             </li> -->
            
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    忘记登录密码怎么办？
                </div>
                <ul class="submenu">
                    <li>在登录页面中选择忘记密码，提供您的绑定手机号后，收到验证码后，按提示进行操作。；如您已登录，则在更多→账户中心→密码管理→登录密码中进行修改。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    手机收不到验证码怎么办？
                </div>
                <ul class="submenu">
                    <li>请先查看手机是否由于资费情况导致无法收到验证码；如仍无法收到融桥宝发送的验证码，可能由于手机运营商处短息较多，请先稍作等待；如还未收到验证码，请联系我们的客服（客服电话：400 007 8655）。</li>
                </ul>
            </li>
            
<!--             <li> -->
<!--                 <div class="link"> -->
<!--                     <i class="fa fa-chevron-down"></i> -->
<!--                     忘记支付密码怎么办? -->
<!--                 </div> -->
<!--                 <ul class="submenu"> -->
<!--                     <li>如忘记支付密码，请及时致电融桥宝客服人员（客服电话：400 007 8655），提供有效证明后，客服人员将会提示您进行密码修改操作。</li> -->
<!--                 </ul> -->
<!--             </li> -->


  <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    为什么要设定支付密码？
                </div>
                <ul class="submenu">
                    <li>之所以建议您设定支付密码，是为了将账户安全保障等级进一步提高，您在融桥宝中，每笔涉及资金交易的操作，均需要支付密码来进行验证。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   支付密码一般用来做什么？
                </div>
                <ul class="submenu">
                    <li>为您在购买融桥宝理财计划或投资工资e账户等资金服务时进行交易认证。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    如何进行支付密码重置？
                </div>
                <ul class="submenu">
                    <li>登录融桥宝APP，选择更多页面—账户中心—支付密码，按页面提示完成密码重置操作。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    我的支付密码为什么会被锁定？
                </div>
                <ul class="submenu">
                    <li>您在融桥宝中在进行资金交易时，连续输错3次支付密码，您的支付密码就会被锁定，届时将无法进行任何资金操作。</li>
                </ul>
            </li>
             <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                      如何解锁支付密码？
                </div>
                <ul class="submenu">
                    <li>在两种情况下，您可能会涉及到密码重置操作
                       <br>1、出于安全考虑，进行主动修改密码操作，进入账户中心—支付密码页面，按页面提示操作即可。
                       <br>2、在进行资金操作时，连续输错3次密码，点击解锁，系统将会跳转至解锁支付密码页面，您可按页面提示修改密码。密码重置成功后，即可回到之前页面。
                       </li>
                </ul>
            </li>
            
            
        </article>
        
        
        
        
        
        <article class="helparticcle">
            <p class="PublicBTP">交易管理：</p>
        </article>
        <article class="PublicArticle BorderTB3f2">
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    什么是账户余额？
                </div>
                <ul class="submenu">
                    <li>融桥宝中的余额即可用来购买e账户或V理财的余额，也可直接提现至银行卡。您在V理财中的投资，如到期，本息将自动转入账户余额。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   提现中的冻结资金是指什么？
                </div>
                <ul class="submenu">
                    <li>冻结资金是指您申购工资e账户时尚未确认产品份额的资金；或提现申请时尚未完成提现流程的资金。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    总资产如何统计？
                </div>
                <ul class="submenu">
                    <li>融桥宝账户总资产=账户余额+投资或提现中冻结金额+工资e账户中的本金及收益+V理财中的投资本金。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    交易记录中有哪些信息？
                </div>
                <ul class="submenu">
                    <li>交易记录中可以查询到您融桥宝账户中的每笔资金变动的明细，您也可以点击筛选，进行分类查找。</li>
                </ul>
            </li>
        </article>
        
        
         <article class="helparticcle" id="pfi">
            <p class="PublicBTP">PFI信用评分：</p>
        </article>
        <article class="PublicArticle BorderTB3f2">
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   PFI信用评分是什么？
                </div>
                <ul class="submenu">
                    <li>PFI信用评分是一套根据财富收入、财富管理行为、履约能力等综合金融信息，结合央行征信系统数据及个人真实信息和所在企业信息，运用大数据及云计算技术客观呈现个人信用状况的评分系统 。</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                    PFI信用评分有什么用？

                </div>
                <ul class="submenu">
                    <li>PFI信用评分将成为您的“经济身份证”， 在逐步开放的V信贷中，您可享受到 “工资先享”等无息信贷服务。我国各类金融机构与商户也将参照您的PFI评分，从而满足未来如分期消费、旅游、购车、租房等多元化的信贷场景需求；也可帮助企业或您的合作伙伴了解您的信用水平，赢得更多口碑。
</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                PFI信用评分是由什么构成？
                </div>
                <ul class="submenu">
                    <li>身份特质：根据您的姓名、年龄、性别、身份证号、手机号以及受教育程度、婚姻状况等个人真实信息进行评定。
<br>收入能力：根据您的财富收入能力来评定，其中将包括诸如工资、额外收入等。
<br>企业授信：根据您所在的工作单位、工作年限、职位、所属企业行业、规模等作为评定依据。
<br>财富特征：以个人财富管理行为、财富管理收益、各类名下资产、过往履约能力等特征作为评定标准。
<br>征信记录：根据人民银行的个人征信记录，包含信用卡、贷款情况、还款记录及电信、移动、电力的缴费情况，公积金和养老保险的缴纳情况，个人法律诉讼及执行信息等进行评定。
</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
              PFI信用评分如何提升？

                </div>
                <ul class="submenu">
                    <li>1、增加更多的金融行为特征，如：在“融桥宝”平台中拥有长期资金沉淀，定期购买理财产品等。
<br>2、个人工作状况更新，如：所在企业规模扩大，公司职位上升，薪资提升等。
<br>3、添加个人银行信用记录， 如：房贷、车贷等。
<br>近期，将开通信息主动录入通道，记录您更为详细的信用特征，进一步提升信用等级。
</li>
                </ul>
            </li>
            
            
              <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
             PFI信用评分更新时间

                </div>
                <ul class="submenu">
                    <li>PFI信用评分将在每个月的6号进行更新，请及时查看，关注您的个人信用等级。

</li>
                </ul>
            </li>
            
            
             <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
             如何查询我的PFI信用评分？

                </div>
                <ul class="submenu">
                    <li>在发现页面您将看到PFI信用评分评分的入口，点击进入后，根据操作提示，填入评分相关数据，即可得到属于您的PFI信用评分。


</li>
                </ul>
            </li>
            
            
             <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
           为什么我的PFI信用评分比朋友低？


                </div>
                <ul class="submenu">
                    <li>PFI信用评分评分是根据您所上传的身份特质、收入能力、企业规模、央行征信记录、财富特征五个维度综合评估得来，如您的分数低于您的朋友，可能是我们对他的这五个维度的相关信息有更全面的了解，我们将会提供更多的信息渠道让您完善个人信息，帮助您提升您的PFI信用评分。

</li>
                </ul>
            </li>
            
        </article>
        
        
        
        
        
        
        
              <article class="helparticcle">
            <p class="PublicBTP">V信贷：</p>
        </article>
        <article class="PublicArticle BorderTB3f2">
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
               什么是V信贷？
                </div>
                <ul class="submenu">
                    <li>V信贷是融桥宝倾力打造的个人信贷系统 ，满足融桥宝用户在小额贷款 、工资预支、分期消费等方面的需求 。
<br>V信贷拥有成熟的风控体系，根据个人金融行为、收入能力、履约能力等综合模型设立消费额度和贷款额度，更具有信用累积，放款快速，安全性高等特点。
 

</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
                   V信贷有什么用？

                </div>
                <ul class="submenu">
                    <li>V信贷可以提前预支次月工资，解决您的短期小额资金需求，还将提供消费金融服务，如分期购买手机、相机等3C数码产品等。融桥宝将根据您信用等级的不断提升为您提供更好、更全面的信用生活服务。

</li>
                </ul>
            </li>
            <li>
                <div class="link">
                    <i class="fa fa-chevron-down"></i>
              我要怎样才能使用V信贷？
                </div>
                <ul class="submenu">
                    <li>V信贷无法主动激活使用， 在保持良好信用生活的前提下，融桥宝将自动向PFI信用评分评分较高的用户逐步开放各项功能，提高自己的财富等级 ，在融桥宝平台中有大量资金沉淀、长期购买理财产品将有助于您更快的开通V信贷服务 。

</li>
                </ul>
            </li>
          
            
        </article>
        
        
        <div class="cbdiv"></div>
    </ul>
    
     <div style="width:100%;height:60px;"></div>

    <article class="PublicArticle BorderT3f2" style="position:fixed;bottom:0;width:100%;margin-bottom:0;">
        <a href="javascript:void" class="PbulickZXa PbulickZXas" style="margin-bottom:8px;border-bottom:0;">
            <img src="<c:url value='/static/h5/img/phoneimg.png'/>" style="float:left;width:27px;height:28px;margin-right:12px;">
            <p style="margin-left:0px;">拨打客服电话:${serviceHotline.phone_no}</p>
            <br><br>
            <pre style="float:left;font-size:0.750em;margin-top:-6px;">${serviceHotline.service_time}</pre>
        </a>
        <div class="cboth"></div>
    </article>
    
    
</body>
<!-- 百度统计代码 wallet-mobile -->
<script>

location.hash="${href}";


var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  //hm.src = "//hm.baidu.com/hm.js\?b0334162ce58a358a7ea396016a5ddea";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
</html>