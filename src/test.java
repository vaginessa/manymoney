import java.util.List;

import com.entity.AccountEntity;
import com.entity.DetailEntity;
import com.entity.WalletEntity;
import com.service.DetailService;
import com.service.QuickTypeService;
import com.service.ReportService;
import com.service.UserService;
import com.service.WalletService;


public class test {
	public static void main(String[] args) {
		ReportService rs=new ReportService();
		System.out.println(rs.getTenDays(6));
	}

}
