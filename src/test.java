import java.util.List;

import com.entity.AccountEntity;
import com.entity.DetailEntity;
import com.entity.WalletEntity;
import com.service.DetailService;
import com.service.QuickTypeService;
import com.service.UserService;
import com.service.WalletService;


public class test {
	public static void main(String[] args) {
		DetailService ws=new DetailService();
		List<DetailEntity> li=ws.getListbyWalletID(1);
		System.out.println(li.size());
	}

}
