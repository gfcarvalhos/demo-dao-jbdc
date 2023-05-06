package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC();   				//maceta para nao expor a implementação, apenas a interface.
	}
}
