
package org.clinicapaciente.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.sql.results.graph.Initializer;
import org.clinicapaciente.model.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCpf;
    @FXML
    private DatePicker txtDataNascimento;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtPesquisar;

    @FXML private TableView<Paciente> tablePacientes;

    @FXML private TableColumn<Paciente, Integer> colID;
    @FXML private TableColumn<Paciente, String> colNome;
    @FXML private TableColumn<Paciente, String> colCpf;
    @FXML private TableColumn<Paciente, String> colTelefone;
    @FXML private TableColumn<Paciente, DatePicker> colDataNascimento;
    @FXML private TableView<Paciente> tabelaDados;
    private int proximoId = 0;
    Paciente paciente;
    List<Paciente> pacienteList;
    ObservableList<Paciente>observableListpacientes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.paciente = new Paciente();
        this.pacienteList = new ArrayList<>();
        vinculoComTabela();
    }
    public void vinculoComTabela(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
    }
    public void lerFormulario(){
        this.paciente.setNome(txtNome.getText());
        this.paciente.setCpf(txtCpf.getText());
        this.paciente.setTelefone(txtTelefone.getText());
        this.paciente.setDataNascimento(txtDataNascimento.getValue());
    }
    public void atualizarTableView(){
        this.pacienteList.forEach(obj -> System.out.println(obj.getNome() + ", " + obj.getCpf() +
                ", " + obj.getTelefone() + ", " + obj.getDataNascimento() + "\n"));
        this.observableListpacientes = FXCollections.observableList(this.pacienteList);
        this.tabelaDados.setItems(this.observableListpacientes);
    }
    @FXML
    protected void onCadastrarClick() {
        lerFormulario();
        int novoId = ++ proximoId;
        this.paciente.setId(novoId);
        this.pacienteList.add(paciente);
        this.paciente = new Paciente();
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtDataNascimento.setValue(null);
    }
    @FXML
    protected void onListPacinteClick(){
        String nomeBuscado = txtNome.getText();

        if (nomeBuscado == null || nomeBuscado.trim().isEmpty()){

            atualizarTableView();
            return;
        } else {
            List<Paciente> filtrados = new ArrayList<>();
            for (Paciente p : pacienteList){

                if (p.getNome().equalsIgnoreCase(nomeBuscado.trim())){
                    filtrados.add(p);
                }
            }
            ObservableList<Paciente> listaFiltrada = FXCollections.observableArrayList(filtrados);
            tabelaDados.setItems(listaFiltrada);
        }

    }
    @FXML
    protected void onAtualizarPacienteClick(){

        // ID digitado no campo
        int id = Integer.parseInt(txtID.getText());

        // Procura o paciente na lista
        Paciente pacienteExistente = null;
        for (Paciente p : pacienteList) {
            if (p.getId() == id) {
                pacienteExistente = p;
                break;
            }
        }

        // Se não encontrou
        if (pacienteExistente == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        // Lê dados do formulário
        lerFormulario();

        // Atualiza os dados do paciente existente
        pacienteExistente.setNome(paciente.getNome());
        pacienteExistente.setCpf(paciente.getCpf());
        pacienteExistente.setTelefone(paciente.getTelefone());
        pacienteExistente.setDataNascimento(paciente.getDataNascimento());

        System.out.println("Paciente atualizado com sucesso!");

        // Limpa os campos
        txtID.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtDataNascimento.setValue(null);

        // Cria novo objeto paciente limpo
        this.paciente = new Paciente();

        tabelaDados.refresh();


    }
    @FXML
    protected void onExcluirPacienteClick() {
        // Verifica se o campo ID está preenchido
        if (txtID.getText().isEmpty()) {
            System.out.println("Informe o ID para excluir.");
            return;
        }

        int id = Integer.parseInt(txtID.getText());

        Paciente pacienteParaExcluir = null;

        // Procura na lista
        for (Paciente p : pacienteList) {
            if (p.getId() == id) {
                pacienteParaExcluir = p;
                break;
            }
        }

        // Se não encontrou
        if (pacienteParaExcluir == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        // Remove da lista
        pacienteList.remove(pacienteParaExcluir);

        // Atualiza tabela
        tabelaDados.refresh();

        System.out.println("Paciente removido com sucesso!");

        // Limpa os campos
        txtID.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtDataNascimento.setValue(null);

        // Cria novo paciente vazio para evitar reaproveitar dados
        this.paciente = new Paciente();

    }
}
