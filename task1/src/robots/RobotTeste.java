package robots;

import java.util.ArrayList;
import java.util.List;

public class RobotTeste {

	public static void main(String[] args) {

		String link = "https://www.apple.com/robots.txt";
		Robot robot = new Robot(link, "files/");
		robot.start();
		System.out.println(robot.getAgentByName("*").toString());
		System.out.println();
		
		List<String> disallow = new ArrayList<String>();
		List<String> allow = new ArrayList<String>();
		
		allow.add("/ac/globalnav/2.0/*/images/ac-globalnav/globalnav/search/*");

		disallow.add("/*/includes/*");
		disallow.add("/*/retail/availability*");
		disallow.add("/*/retail/availabilitySearch*");
		disallow.add("/*/retail/pickupEligibility*");
		disallow.add("/*/shop/signed_in_account*");
		disallow.add("/*/shop/sign_in*");
		disallow.add("/*/shop/sign_out*");
		disallow.add("/*/shop/*WebObjects/*");
		disallow.add("/*/shop/1-800-MY-APPLE/*");
		disallow.add("/*/shop/answer/vote*");
		disallow.add("/*/shop/bag*");
		disallow.add("/*/shop/browse/overlay/*");
		disallow.add("/*/shop/browse/ribbon/*");
		disallow.add("/*/shop/browse/campaigns/mobile_overlay*");
		disallow.add("/*/shop/button_availability*");
		disallow.add("/*/shop/favorites*");
		disallow.add("/*/shop/iphone/payments/overlay/*");
		disallow.add("/*/shop/mobile/olss_error*");
		disallow.add("/*/shop/mobilex/*");
		disallow.add("/*/shop/order/*");
		disallow.add("/*/shop/question/answer/report*");
		disallow.add("/*/shop/question/subscribe*");
		disallow.add("/*/shop/question/unsubscribe/");
		disallow.add("/*/shop/review/vote*");
		disallow.add("/*/shop/reviews/report*");
		disallow.add("/*/shop/rs-mvt/rel/*");
		disallow.add("/*/shop/sentry*");
		disallow.add("/*/shop/socialsharing/*");
		disallow.add("/*/shop/store/feeds/*");
		disallow.add("/*/shop/variationSelection");
		disallow.add("/*_adc_*/shop/");
		disallow.add("/*_aoc_*/shop/");
		disallow.add("/*_edu_*/shop/");
		disallow.add("/*_enterprise*/shop/");
		disallow.add("/*_internal-epp-discounted*/shop/");
		disallow.add("/*_k12nonbts*/shop/");
		disallow.add("/*_kiosk*/shop/");
		disallow.add("/*_nonbts*/shop/");
		disallow.add("/*_qpromo*/shop/");
		disallow.add("/*_refurb-discounted*/shop/");
		disallow.add("/cn/*/aow/*");
		disallow.add("/go/awards/*");
		disallow.add("/newsroom/notifications/*");
		disallow.add("/retail/availability*");
		disallow.add("/retail/availabilitySearch*");
		disallow.add("/retail/pickupEligibility*");
		disallow.add("/shop/*signed_in_account*");
		disallow.add("/shop/*sign_in*");
		disallow.add("/shop/*sign_out*");
		disallow.add("/shop/*WebObjects/*");
		disallow.add("/shop/1-800-MY-APPLE/*");
		disallow.add("/shop/answer/vote*");
		disallow.add("/shop/bag*");
		disallow.add("/shop/browse/campaigns/mobile_overlay*");
		disallow.add("/shop/button_availability*");
		disallow.add("/shop/favorites*");
		disallow.add("/shop/mobile/olss_error*");
		disallow.add("/shop/mobilex/");
		disallow.add("/shop/order/*");
		disallow.add("/shop/question/answer/report*");
		disallow.add("/shop/question/subscribe*");
		disallow.add("/shop/question/unsubscribe/");
		disallow.add("/shop/review/vote*");
		disallow.add("/shop/reviews/report*");
		disallow.add("/shop/rs-mvt/rel/*");
		disallow.add("/shop/search/*");
		disallow.add("/shop/sentry*");
		disallow.add("/shop/socialsharing/*");
		disallow.add("/shop/store/feeds/*");
		disallow.add("/shop/variationSelection*");
		disallow.add("/tmall*");
		
		
//		for (String s : robot.getAgentByName("*").getAllow()){
//			System.out.println("allow.add(\""+s+"\");");
//		}
//		System.out.println();
//		for (String s : robot.getAgentByName("*").getDisallow()){
//			System.out.println("disallow.add(\""+s+"\");");
//		}
		
		
		List<String> list = new ArrayList<String>();
		list.add("https://www.apple.com/iphone/");
		list.add("https://www.apple.com/robots.txt");
		list.add("https://www.apple.com/shop");
		list.add("https://www.apple.com/tmall");
		list.add("https://www.apple.com/blabla_adc_teste/shop/");
		list.add("https://www.apple.com/sdss/includes/sassa");
		list.add("https://www.apple.com/br/shop/buy-iphone/iphone-7");
		
		
//		String sa = "https://www.apple.com" + "/*/includes/*".replaceAll("\\*", ".*");
//		for(String sz : disallow){
//			String rejex = "https://www.apple.com" + sz.replaceAll("\\*", ".*");
//			System.out.println(rejex+"\n");
//			for(String s : list){
//				System.out.println(s + " - " + s.matches(rejex));
//			}
//			System.out.println("----------------------------------");
//		}
		
		
		List<String> disallow1 = new ArrayList<String>();
		disallow1.add("/");
		disallow1.add("/us/shop/837");
		disallow1.add("/us/shop/837/*");
		disallow1.add("/us/epp/*");
		disallow1.add("/us/api/*");
		disallow1.add("/us/dealer/*");
		disallow1.add("/us/search/");
		disallow1.add("/us/business/search/");
		disallow1.add("/*/business/search/");

		List<String> allow1 = new ArrayList<String>();
		allow1.add("/us/*");
		allow1.add("/*/business/*");
		allow1.add("/*/support/*");
		allow1.add("/global/seriftv/");
		allow1.add("/ca/ses/*");
		allow1.add("/ca_fr/ses/*");
		
		String site = "https://www.apple.com";
		
		List<String> list1 = new ArrayList<String>();
		list1.add("/us/shop/837/iphone/");
		list1.add("/teste/sasasa/sasass/business/search/");
		
		for(String sz : disallow1){
			String rejex = site + sz.replaceAll("\\*", ".*");
			System.out.println(rejex+"\n");
			for(String s : list1){
				String ss = site + s;
				System.out.println(ss + " - " + ss.matches(rejex));
			}
			System.out.println("----------------------------------");
		}
	}

}
