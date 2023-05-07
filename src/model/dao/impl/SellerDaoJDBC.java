package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;			//Criando uma dependencia com a conexao com o BD
	
	public SellerDaoJDBC(Connection conn) {		//Força a dependencia
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ? ");
			
			st.setInt(1, id);
			rs = st.executeQuery();			//Vem em formato tabela
			if(rs.next()) {					//Transformar em OO
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
				
			}
			return null;			//Retorna nulo quando não existe a info no BD
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBasesalary(rs.getDouble("BaseSalary"));
		obj.setBithdate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {	//melhora usando instanciação com propagação do erro
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();			//Vem em formato tabela
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();			//Semelhante ao dicionário
			
			while (rs.next()) {					//Transformar em OO
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {				//Verificando se dep ja axiste, caso nao cria um departamento e associa ao seller na lista
					dep = instantiateDepartment(rs);	
					map.put(rs.getInt("DepartmentId"), dep);
				}								//Verificação necessaria para nao criar + de 1 dep com o mesmo nome/id
				
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
				
			}
			return list;			//Retorna nulo quando não existe a info no BD
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
