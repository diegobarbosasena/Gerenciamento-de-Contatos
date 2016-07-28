package br.com.contatos.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.com.contatos.helper.MySqlConnect;
import br.com.contatos.model.Contatos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ContatoController implements Initializable{

	@FXML TextField txtNome;
	@FXML TextField txtTelefone;
	@FXML Button btnInserir;
	@FXML ListView<Contatos> lstContatos;
	@FXML Button btnEditar;
	@FXML Button btnDeletar;

	private void preencherLista() {

		lstContatos.getItems().clear();

		Connection con = MySqlConnect.ConectarDb();

		String sql_select ="SELECT * FROM contact;";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql_select);

			while(rs.next() ){

				Contatos c = new Contatos();

				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("name"));
				c.setTelefone(rs.getString("phone"));

				String nome = rs.getString("name");
				String telefone = rs.getString("phone");

				lstContatos.getItems().add(c);
			}
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		preencherLista();
	}

	@FXML public void inserirContato() {

		Connection con = MySqlConnect.ConectarDb();

		String sql_insert ="INSERT INTO contact (name, phone) VALUES( ?, ?);";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql_insert);
			parametros.setString(1, txtNome.getText());
			parametros.setString(2, txtTelefone.getText());

			parametros.executeUpdate();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		preencherLista();

		txtNome.clear();
		txtTelefone.clear();

		System.out.println("INSERIDO COM SUCESSO!!!");
	}

	@FXML public void deletarContato() {

		Contatos contatos = lstContatos.getSelectionModel().getSelectedItem();

		Connection con = MySqlConnect.ConectarDb();
		String sql_deletar ="DELETE FROM contact WHERE id = ?;";
		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql_deletar);
			parametros.setInt(1, contatos.getId());
			parametros.executeUpdate();

			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		preencherLista();

		System.out.println("DELETADO COM SUCESSO!!!");
	}

	@FXML public void editarContato() {

		Contatos contatos = lstContatos.getSelectionModel().getSelectedItem();

		Connection con = MySqlConnect.ConectarDb();
		String sql_select_atualizar ="SELECT * FROM contact WHERE id = ?;";
		PreparedStatement parametros;

		try {
			ResultSet rs = con.createStatement().executeQuery(sql_select_atualizar);



			while (rs.next()) {

				Contatos c = new Contatos();

				parametros = con.prepareStatement(sql_select_atualizar);
				parametros.setInt(1, contatos.getId());
				parametros.executeQuery(sql_select_atualizar);

				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("name"));
				c.setTelefone(rs.getString("phone"));

				String id = rs.getString("id");
				String nome = rs.getString("name");
				String tel = rs.getString("phone");

				txtNome.setText(nome);
				txtTelefone.setText(tel);



				con.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//String sql_atualizar ="ALTER TABLE contact WHERE id = ?;";

	}

}
