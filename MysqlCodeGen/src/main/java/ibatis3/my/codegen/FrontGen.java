package main.java.ibatis3.my.codegen;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * iBatis3，代码生成器
 * 基于Velocity引擎
 * 
 * @author yangxt
 * 
 */
public class FrontGen {
	
	private static String sys = "ctrading";

	/**
	 * 运行
	 */
	public static void main(String[] args) {
		try {
			
////			gen("/account/open/","account","/account/tradeaccount","open",new String[] {"openPaymentList","open","openQuestionnaire","openRiskRating","openResult"});
////			gen("/account/FCopen","account","/account/tradeaccount","greenChannel",new String[] {"greenChannel","greenChannelResult"});
////			gen("/fund/buy","trade","/trade","buy",new String[] {"buyableFundList","buyInput","buyConfirm","buyPay","buyResult"});
////			gen("/fund/subscription","trade","/trade","subscribe",new String[] {"subscribeInput","subscribeConfirm","subscribePay","subscribeResult"});
////			gen("/fund/switch","trade","/trade","convert",new String[] {"convertList","convertInput","convertConfirm","convertResult"});
////			gen("/fund/redemption","trade","/trade","redeem",new String[] {"redeemList","redeemInput","redeemConfirm","redeemResult"});
////			gen("/fund/revocation","trade","/trade","withdraw",new String[] {"withdrawList","withdrawConfirm","withdrawResult"});
////			gen("/fund/trustin","trade","/trade","trusteein",new String[] {"trusteeinInput","trusteeinConfirm","trusteeinResult"});
////			gen("/fund/dividend","trade","/trade","dividendmodify",new String[] {"dividendmodifyList","dividendmodifyInput","dividendmodifyResult"});
////			gen("/fund/RSP/manage/","plan","/plan","buyPlanManage",new String[] {"tradePlanList","tradePlanDetail","buyPlanPayHistory"});
////			gen("/fund/RSP/add/","plan","/plan","buyPlanAdd",new String[] {"buyPlanFundList","buyPlanInput","buyPlanConfirm","buyPlanResult"});
////			gen("/fund/RSP/modify/","plan","/plan","buyPlanEdit",new String[] {"buyPlanEdit","buyPlanEditConfirm","buyPlanEditResult"});
////			gen("/fund/RSP/stop/","plan","/plan","buyPlanStop",new String[] {"buyPlanStopConfirm","buyPlanStopResult"});
////			gen("/fund/ar","plan","/plan","redeemPlan",new String[] {"redeemPlanList","redeemPlanInput","redeemPlanConfirm","redeemPlanResult","redeemPlanStop","redeemPlanStopResult"});
////			gen("/fund/rsswitch/add","plan","/plan","convertPlan",new String[] {"convertPlanInput","convertPlanConfirm","convertPlanResult"});
////			gen("/fund/rsswitch/manage","plan","/plan","convertPlanManage",new String[] {"convertPlanList","convertAgreement"});
////			gen("/fund/rsswitch/modify","plan","/plan","convertPlanEdit",new String[] {"convertPlanEdit","convertPlanEditConfirm","convertPlanEditResult"});
////			gen("/fund/rsswitch/stop","plan","/plan","convertPlanStop",new String[] {"convertPlanStopConfirm","convertPlanStopResult"});
////			gen("/account/payment/add","capital","/account/currentaccount","paymentAdd",new String[] {"paymentAddList","paymentAddInput","paymentAddConfirm"});
//			gen("/account/payment/status","capital","/account/currentaccount","paymentStatus",new String[] {"paymentStatusList"});
////			gen("/account/payment/modify","capital","/account/currentaccount","paymentEdit",new String[] {"paymentEditList","paymentCardEdit","paymentBankEdit","paymentEditConfirm"});
////			gen("/account/idnumber/switch","capital","/account/currentaccount","IdCardConvert",new String[] {"IdCardConvert","IdCardConvertConfirm"});
////			gen("/account/autopay/manage","capital","/account/currentaccount","autoPayAgreement",new String[] {"autoPayAgreementList","autoPayAgreementSign","autoPayAgreementSignResult","autoPayAgreementUnsign","gdbCardConvert"});
////			gen("/account/profile/modify","capital","/account/tradeaccount","accountEdit",new String[] {"accountEdit","accountEditConfirm"});
////			gen("/account/password/modify","capital","/account/tradeaccount","passwordEdit",new String[] {"passwordEdit","passwordEditResult"});
////			gen("/account/riskTest/modify","capital","/account/tradeaccount","anweerSheetEdit",new String[] {"anweerSheetEdit","anweerSheetEditRating","anweerSheetEditResult"});
////			gen("/query/applyments/today","query","/query","todayApplication",new String[] {"todayApplication","tradeDetail"});
////			gen("/query/applyments/history","query","/query","historyApplication",new String[] {"historyApplication","tradeDetail","historyApplymentsResult"});
////			gen("/query/confirm","query","/query","confirmQuery",new String[] {"confirmQuery","confirmQueryResult"});
////			gen("/query/dividend/history","query","/query","historyDividen",new String[] {"historydividen","historydividenResult"});
////			gen("/query/accountmodification","query","/query","accountEditQuery",new String[] {"accountEditQuery","accountEditQueryResult","accountDetails"});
////			gen("/account/password/reset","account","/account/tradeaccount","resetPassword",new String[] {"paymentList","resetPasswordIdentity","resetPassword","resetPasswordResult"});
			
			
			gen("/login","sys","/","login",new String[] {"login"});
			gen("/main","sys","/","main",new String[] {"main"});
			gen("/passwordupdate","sys","/","passwordUpdate",new String[] {"passwordUpdate"});
			gen("/logout","sys","/","logout",new String[] {"logout"});
			gen("/account/individualopen/","account","/account/tradeaccount","individualOpen",new String[] {"individualOpen","openQuestionnaire","openRiskRating","operationResult"});
			gen("/account/institutionopen/","account","/account/tradeaccount","institutionOpen",new String[] {"institutionOpen","operationResult"});
			/*gen("/account/counterwayopen","account","/account/tradeaccount","counterChannelOpen",new String[] {"counterChannelOpen","operationResult"});
			gen("/account/tradeaccountclose","account","/account/tradeaccount","tradeAccountClose",new String[] {"tradeAccountClose","operationResult"});
			gen("/account/fundaccountclose/","account","/account/tradeaccount","fundAccountClose",new String[] {"fundAccountClose","operationResult"});
			gen("/account/individualedit/","account","/account/tradeaccount","individualEdit",new String[] {"individualEdit","operationResult"});
			gen("/account/institutionedit/","account","/account/tradeaccount","institutionEdit",new String[] {"institutionEdit","operationResult"});
			gen("/currentaccount/edit","account","/account/currentaccount","currentAccountEdit",new String[] {"currentAccountEdit","operationResult"});
			gen("/currentaccount/editfornet","account","/account/currentaccount","currentAccountEditForNet",new String[] {"currentAccountEditForNet","operationResult"});
			gen("/account/fundaccountapply/","account","/account/tradeaccount","fundAccountApply",new String[] {"fundAccountApply","operationResult"});
			gen("/account/fundaccountregister/","account","/account/tradeaccount","fundAccountRegister",new String[] {"fundAccountRegister","operationResult"});
			gen("/account/questionnair","account","/account/tradeaccount","questionnaire",new String[] {"questionnaire","riskRating","operationResult"});
			gen("/account/passwordReset","accountx","/account/tradeaccount","passwordReset",new String[] {"passwordReset","operationResult"});
			gen("/account/passwordUpdate","accountx","/account/tradeaccount","passwordUpdate",new String[] {"passwordUpdate","operationResult"});
			gen("/account/unlock","accountx","/account/tradeaccount","unlock",new String[] {"unlock","operationResult"});
			gen("/fund/buy","trade","/trade","buy",new String[] {"buyInput","operationResult"});
			gen("/fund/subscription","trade","/trade","subscribe",new String[] {"subscribeInput","operationResult"});
			gen("/fund/redemption","trade","/trade","redeem",new String[] {"redeemInput","operationResult"});
			gen("/fund/redemption","trade","/trade","specialredeem",new String[] {"specialRedeemInput","operationResult"});
			gen("/fund/convert","trade","/trade","convert",new String[] {"convertInput","operationResult"});
			gen("/fund/trustoutin","tradex","/trade","trusteeOutIn",new String[] {"trusteeOutInInput","operationResult"});
			gen("/fund/trustout","tradex","/trade","trusteeOut",new String[] {"trusteeOutInput","operationResult"});
			gen("/fund/trustin","tradex","/trade","trusteeIn",new String[] {"trusteeInInput","operationResult"});
			gen("/fund/dividend","tradex","/trade","dividendmodify",new String[] {"dividendmodifyInput","operationResult"});
			gen("/fund/revocation","tradex","/trade","withdraw",new String[] {"withdrawList","operationResult"});
			gen("/capital/deposit","capital","/capital","deposit",new String[] {"depositInput","operationResult"});
			gen("/query/fundinfo","query","/query","fundInfo",new String[] {"fundInfo","fundInfoDetail"});
			gen("/query/fundaccountinfo","query","/query","fundAccountInfo",new String[] {"fundAccountInfo","fundAccountInfoDetail"});
			gen("/query/tradeaccountinfo","query","/query","tradeAccountInfo",new String[] {"tradeAccountInfo","tradeAccountInfoDetail"});
			gen("/query/customershare","query","/query","customerShare",new String[] {"customerShare","customerShareDetail"});
			gen("/query/capitalBalance","query","/query","capitalBalance",new String[] {"capitalBalance","capitalBalanceDetail"});
			gen("/query/sharedetail","query","/query","shareDetail",new String[] {"shareDetailQuery","shareDetailInfo"});
			gen("/query/customerbank","query","/query","customerBank",new String[] {"customerBank"});
			gen("/queryx/traderequest","queryx","/queryx","tradeRequest",new String[] {"tradeRequest","tradeRequestDetail"});
			gen("/queryx/traderequestconfirm","queryx","/queryx","tradeRequestConfirm",new String[] {"requestConfirm","tradeRequestConfirmDetail"});
			gen("/queryx/taconfirm","queryx","/queryx","taConfirm",new String[] {"taConfirm","taConfirmDetail"});
			gen("/queryx/accountrequest","queryx","/queryx","accountRequest",new String[] {"accountRequest","accountRequestDetail"});
			gen("/queryx/accountconfirm","queryx","/queryx","accountConfirm",new String[] {"accountConfirm","accountConfirmDetail"});
			gen("/queryx/questionnaire","queryx","/queryx","questionnaire",new String[] {"questionnaireQuery","questionnaireDetail"});
			gen("/queryx/capital","queryx","/queryx","capital",new String[] {"capital","capitalDetail"});
			gen("/queryx/deposit","queryx","/queryx","deposit",new String[] {"deposit","depositDetail"});
			gen("/queryx/share","queryx","/queryx","share",new String[] {"shareList","shareDetail"});
			gen("/queryx/dividend","queryx","/queryx","dividend",new String[] {"dividend","dividendDetail"});
			gen("/queryx/check","queryx","/queryx","check",new String[] {"check","checkDetail"});
			gen("/check/tradecheck","check","/check","tradeCheck",new String[] {"tradeCheck","tradeCheckBuy","tradeCheckSubscribe","tradeCheckRedeem","tradeCheckSpecialredeem","tradeCheckConvert","tradeCheckTrusteeOutIn","tradeCheckTrusteeOut","tradeCheckTrusteeIn","tradeCheckDividendmodify"});
			gen("/check/tradecheckedit","check","/check","tradeCheckEdit",new String[] {"tradeCheckEdit","tradeCheckEditBuy","tradeCheckEditSubscribe","tradeCheckEditRedeem","tradeCheckEditSpecialredeem","tradeCheckEditConvert","tradeCheckEditTrusteeOutIn","tradeCheckEditTrusteeOut","tradeCheckEditTrusteeIn","tradeCheckEditDividendmodify"});
			gen("/check/accountcheck","check","/check","accountCheck",new String[] {"accountCheck","accountCheckIndividualOpen","accountCheckInstitutionOpen","accountCheckCounterChannelOpen","accountCheckTradeAccountClose","accountCheckFundAccountClose","accountCheckIndividualEdit","accountCheckInstitutionEdit","accountCheckCurrentAccountEdit","accountCheckCurrentAccountEditForNet","accountCheckFundAccountApply","accountCheckFundAccountRegister"});
			gen("/check/accountcheckedit","check","/check","accountCheckEdit",new String[] {"accountCheckEdit","accountCheckEditIndividualOpen","accountCheckEditInstitutionOpen","accountCheckEditCounterChannelOpen","accountCheckEditTradeAccountClose","accountCheckEditFundAccountClose","accountCheckEditIndividualEdit","accountCheckEditInstitutionEdit","accountCheckEditCurrentAccountEdit","accountCheckEditCurrentAccountEditForNet","accountCheckEditFundAccountApply","accountCheckEditFundAccountRegister"});
			gen("/check/deposit","check","/check","deposit",new String[] {"deposit"});
			gen("/check/depositedit","check","/check","depositEdit",new String[] {"depositEdit"});
			gen("/print/confirmletter","print","/print","confirmLetter",new String[] {"confirmLetter","confirmLetterDetail"});
			gen("/print/voucher","print","/print","voucher",new String[] {"voucher","voucherDetail"});
			gen("/print/batchprint","print","/print","batchPrint",new String[] {"batchPrint","batchPrintDetail"});
*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成
	 */
	/**
	 * @param url TODO
	 * @param menuGrp
	 * @param ftlPath TODO
	 * @param menu
	 * @param menuSteps
	 * @throws Exception
	 */
	private static void gen(String url, String menuGrp, String ftlPath, String menu, String[] menuSteps) throws Exception {
		VelocityContext context = new VelocityContext();
		
		context.put("sys", sys );
		context.put("dateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		context.put("url", url);
		context.put("menugrp", menuGrp);
		context.put("ftlpath", ftlPath);
		context.put("menulowercase", menu.toLowerCase());
		context.put("lmenu", menu);
		context.put("menu", menu.substring(0, 1).toUpperCase()+menu.substring(1));
		context.put("steps", menuSteps);
		
		List<String> firstUpperMenuSteps = new ArrayList<String>();
		for(String step:menuSteps)
		{
			firstUpperMenuSteps.add(step.substring(0, 1).toUpperCase()+step.substring(1));
		}
		context.put("firstuppersteps", firstUpperMenuSteps);
		
		
		VelocityEngine engine = new VelocityEngine();
		Properties properties = new Properties();
		properties.setProperty(Velocity.RESOURCE_LOADER, "file");
		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "vm");
		engine.init(properties);
		
		String dtoPath = "target/dto/ds/"+sys+"/"+menuGrp.toLowerCase()+"/"+menu.toLowerCase()+"/";
		String controllerPath = "target/"+sys+"/ds/"+sys+"/"+menuGrp.toLowerCase()+"/"+menu.toLowerCase()+"/";
		String voPath = "target/"+sys+"/ds/"+sys+"/"+menuGrp.toLowerCase()+"/"+menu.toLowerCase()+"/";
		String facadePath = "target/"+sys+"Facade/ds/"+sys+"/facade/"+menuGrp.toLowerCase()+"/";
		
		File path = new File(dtoPath);
		if(!path.exists())
		{
			path.mkdirs();
		}
		path = new File(controllerPath);
		if(!path.exists())
		{
			path.mkdirs();
		}
		path = new File(voPath);
		if(!path.exists())
		{
			path.mkdirs();
		}
		path = new File(facadePath);
		if(!path.exists())
		{
			path.mkdirs();
		}
		
		String[] vms = { "controller.vm", "dto.vm", "vo.vm","facade.vm","facadeimpl.vm" };
		for (String vm : vms) {
			if (vm.equals("controller.vm")) {
				Template template = engine.getTemplate(vm, "UTF-8");
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
						controllerPath+ convertAs(menu, vm))), "UTF-8"));
				template.merge(context, writer);
				writer.flush();
				writer.close();
			} else if (vm.equals("facade.vm") || vm.equals("facadeimpl.vm")) {
					Template template = engine.getTemplate(vm, "UTF-8");
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
							facadePath+ convertAs(menu, vm))), "UTF-8"));
					template.merge(context, writer);
					writer.flush();
					writer.close();
			} else if (vm.equals("vo.vm") || vm.equals("dto.vm")) {
				for (String step : menuSteps) {
					context.put("menustep", step.substring(0,1).toUpperCase()+step.substring(1));
					context.put("lmenustep", step);
					String filename;
					if (vm.equals("vo.vm")) {
						 filename = voPath+ convertAs(step, vm);
					}
					else {
						 filename = dtoPath+ convertAs(step, vm);
					}
					
					Template template = engine.getTemplate(vm, "UTF-8");
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
							filename)), "UTF-8"));
					template.merge(context, writer);
					writer.flush();
					writer.close();
					
				}
			}
		}
	}
	
	/**
	 * 生成文件名，首字母大写
	 */
	private static String convertAs(String menu, String vm_name) throws Exception {

		String[] parts = menu.toLowerCase().split("_");
		String file = "";
//		for(int i=1; i<parts.length; i++)
			file += menu.substring(0,1).toUpperCase() + menu.substring(1); 

		if (vm_name.equals("controller.vm"))
			return file + "Controller.java";
		else if (vm_name.equals("dto.vm"))
			return file + "Dto.java";
		else if (vm_name.equals("vo.vm"))
			return file + "Vo.java";
		else if (vm_name.equals("facade.vm"))
			return file + "Facade.java";
		else if (vm_name.equals("facadeimpl.vm"))
			return file + "FacadeImpl.java";
		else
			throw new RuntimeException("God knows");
	}
	
}
