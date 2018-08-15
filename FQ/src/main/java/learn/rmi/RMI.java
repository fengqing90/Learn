// package learn. rmi;
//
// import java.net.MalformedURLException;
// import java.rmi.Naming;
// import java.rmi.NotBoundException;
// import java.rmi.RemoteException;
// import java.util.List;
//
// import org.junit.Test;
//
// import com.atsig.uac.bean.AccountBean;
// import com.atsig.uac.bean.DataRolePermBean;
// import com.atsig.uac.bean.FuncBean;
// import com.atsig.uac.bean.UserPermissions;
// import com.atsig.uac.rmi.UACRmiService;
//
//
// public class RMI {
// @Test
// public void testRMI() {
// String port = "8601";
// String ip = "192.168.3.188";
// try {
// AccountBean account = new AccountBean();
// account.setAccountName("TEST_7");
// account.setAccountPwd("123456");
// System.out.println("RMI Address = //" + ip + ":" + port + "/UACRmiService");
// UACRmiService service = (UACRmiService) Naming.lookup("//" + ip + ":" + port
// + "/UACRmiService");
// UserPermissions userPermissions = service.checkAccount(account);
// List<FuncBean> funList = userPermissions.getFuncRoleList();
// System.out.println("功能角色 size:" + funList.size());
// for (FuncBean bean : funList) {
// // System.out.println("id:" + bean.getFuncId() + " | " +
// bean.getFuncName()+" | "+bean.getFuncOpt()+":"+bean.getOptCap());
// System.out.println(bean);
// }
//
// List<DataRolePermBean> dataList = userPermissions.getDataRoleList();
// if(dataList!=null){
// System.out.println("数据角色 size:" + dataList.size());
// for (DataRolePermBean bean : dataList){
// // System.out.println("id:" + bean.getDataPermId() + " | " +
// bean.getDataPermName());
// System.out.println(bean);
// }
// }
//
// } catch (MalformedURLException e) {
// e.printStackTrace();
// } catch (RemoteException e) {
// e.printStackTrace();
// } catch (NotBoundException e) {
// e.printStackTrace();
// }
//
// }
//}