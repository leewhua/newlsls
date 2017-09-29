import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.MixnickGetRequest;
import com.taobao.api.request.TmallMarketingTmallcouponCouponApplyRequest;
import com.taobao.api.response.MixnickGetResponse;
import com.taobao.api.response.TmallMarketingTmallcouponCouponApplyResponse;

public class tmallcoupontest {
	public static void main(String[] s) throws Exception{
		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23453654", "50611b2c3a1bcca88adf616f4f186155");
		MixnickGetRequest req1 = new MixnickGetRequest();
		req1.setNick("richardmajor");
		MixnickGetResponse rsp1 = client.execute(req1);
		System.out.println(rsp1.getBody());
		
		//TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23453654", "50611b2c3a1bcca88adf616f4f186155");
		TmallMarketingTmallcouponCouponApplyRequest req = new TmallMarketingTmallcouponCouponApplyRequest();
		req.setFaceAmount(1000L);
		req.setNick(rsp1.getNick());
		TmallMarketingTmallcouponCouponApplyResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
	}
}
