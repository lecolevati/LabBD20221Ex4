package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class ClienteDao implements IClienteDao {

	private GenericDao gDao;
	
	public ClienteDao(GenericDao gDao) {
		this.gDao = gDao;
	}
	
	@Override
	public String iudCliente(String op, Cliente cli) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		
		String sql = "{CALL sp_iud_cliente (?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, op);
		cs.setString(2, cli.getCpf());
		cs.setString(3, cli.getNome());
		cs.setString(4, cli.getEmail());
		cs.setFloat(5, cli.getLimite());
		cs.setString(6, cli.getDtNasc());
		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(7);
		cs.close();
		c.close();
		
		return saida;
	}

	@Override
	public Cliente findCliente(Cliente cli) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		
		String sql = "SELECT cpf, nome, email, limite_credito, dt_nasc FROM cliente WHERE cpf = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cli.getCpf());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			cli.setCpf(rs.getString("cpf"));
			cli.setNome(rs.getString("nome"));
			cli.setEmail(rs.getString("email"));
			cli.setLimite(rs.getFloat("limite_credito"));
			cli.setDtNasc(rs.getString("dt_nasc"));
		}
		
		rs.close();
		ps.close();
		c.close();
		return cli;

	}

	@Override
	public List<Cliente> findClientes() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUBSTRING(cpf,1,3)+'.'+SUBSTRING(cpf,4,3)+'.'+SUBSTRING(cpf,8,3)+'-'+SUBSTRING(cpf,10,2) AS cpf, ");
		sql.append("nome, email, limite_credito, ");
		sql.append("CONVERT(CHAR(10), dt_nasc, 103) AS dt_nasc FROM cliente");
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Cliente cli = new Cliente();
			
			cli.setCpf(rs.getString("cpf"));
			cli.setNome(rs.getString("nome"));
			cli.setEmail(rs.getString("email"));
			cli.setLimite(rs.getFloat("limite_credito"));
			cli.setDtNasc(rs.getString("dt_nasc"));
			
			clientes.add(cli);
		}
		
		rs.close();
		ps.close();
		c.close();
		
		return clientes;
	}

}
