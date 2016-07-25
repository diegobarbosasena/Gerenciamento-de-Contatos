package br.com.contatos.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.com.contatos.helper.MySqlConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ContatoController implements Initializable{

	@FXML TextField txtNome;
	@FXML TextField txtTelefone;
	@FXML Button btnInserir;
	@FXML ListView lstContatos;

	private void preencherLista() {

		lstContatos.getItems().clear();

		Connection con = MySqlConnect.ConectarDb();

		String sql_select ="SELECT * FROM contact;";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql_select);

			while(rs.next() ){
				String nome = rs.getString("name");
				String telefone = rs.getString("phone");

				lstContatos.getItems().add(nome + " - " + telefone);
			}

			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		System.out.println("funcionou");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		//metodo para preencher a lista
		preencherLista();
	}

}
