package org.clinicapaciente.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.clinicapaciente.model.Paciente;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private TextField txtID;
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private DatePicker txtDataNascimento;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtPesquisar;

    @FXML private TableView<Paciente> tabelaDados;

    @FXML private TableColumn<Paciente, Integer> colID;
    @FXML private TableColumn<Paciente, String> colNome;
    @FXML private TableColumn<Paciente, String> colCpf;
    @FXML private TableColumn<Paciente, String> colTelefone;

    // CORREÇÃO IMPORTANTE → ERA DatePicker, agora é LocalDate
    @FXML private TableColumn<Paciente, LocalDate> colDataNascimento;

    private int proximoId = 0;

    private Paciente paciente;
    private List<Paciente> pacienteList;
    private ObservableList<Paciente> observableListpacientes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.paciente = new Paciente();
        this.pacienteList = new ArrayList<>();
        vinculoComTabela();
    }

    public void vinculoComTabela() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
    }

    public void lerFormulario() {
        this.paciente.setNome(txtNome.getText());
        this.paciente.setCpf(txtCpf.getText());
        this.paciente.setTelefone(txtTelefone.getText());
        this.paciente.setDataNascimento(txtDataNascimento.getValue());
    }

    public void atualizarTableView() {
        this.observableListpacientes = FXCollections.observableList(this.pacienteList);
        this.tabelaDados.setItems(this.observableListpacientes);
    }

    @FXML
    protected void onCadastrarClick() {
        lerFormulario();
        int novoId = ++proximoId;
        this.paciente.setId(novoId);

        this.pacienteList.add(paciente);
        this.paciente = new Paciente();

        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtDataNascimento.setValue(null);
    }

    @FXML
    protected void onListPacinteClick() {
        String nomeBuscado = txtNome.getText();

        if (nomeBuscado == null || nomeBuscado.trim().isEmpty()) {
            atualizarTableView();
            return;
        }

        List<Paciente> filtrados = new ArrayList<>();

        for (Paciente p : pacienteList) {
            if (p.getNome().equalsIgnoreCase(nomeBuscado.trim())) {
                filtrados.add(p);
            }
        }

        ObservableList<Paciente> listaFiltrada = FXCollections.observableArrayList(filtrados);
        tabelaDados.setItems(listaFiltrada);
    }

    @FXML
    protected void onAtualizarPacienteClick() {

        if (txtID.getText().isEmpty()) {
            System.out.println("Informe o ID para atualizar.");
            return;
        }

        int id = Integer.parseInt(txtID.getText());

        Paciente pacienteExistente = null;

        for (Paciente p : pacienteList) {
            if (p.getId() == id) {
                pacienteExistente = p;
                break;
            }
        }

        if (pacienteExistente == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        lerFormulario();

        pacienteExistente.setNome(paciente.getNome());
        pacienteExistente.setCpf(paciente.getCpf());
        pacienteExistente.setTelefone(paciente.getTelefone());
        pacienteExistente.setDataNascimento(paciente.getDataNascimento());

        System.out.println("Paciente atualizado com sucesso!");

        txtID.clear();
        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtDataNascimento.setValue(null);

        this.paciente = new Paciente();

        tabelaDados.refresh();
    }

    @FXML
    protected void onExcluirPacienteClick() {

        if (txtID.getText().isEmpty()) {
            System.out.println("Informe o ID para excluir.");
            return;
        }

        int id = Integer.parseInt(txtID.getText());

        Paciente pacienteParaExcluir = null;

        for (Paciente p : pacienteList) {
            if (p.getId() == id) {
                pacienteParaExcluir = p;
                break;
            }
        }

        if (pacienteParaExcluir == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }

        pacienteList.remove(pacienteParaExcluir);

        tabelaDados.refresh();

        System.out.println("Paciente removido com sucesso!");

        txtID.clear();
        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtDataNascimento.setValue(null);

        this.paciente = new Paciente();
    }
}
