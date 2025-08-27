package com.br.consultas.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import com.br.consultas.controller.AgendamentoController;
import com.br.consultas.controller.MedicoController;
import com.br.consultas.controller.PacienteController;
import com.br.consultas.controller.PessoaController;
import com.br.consultas.model.Agendamento;
import com.br.consultas.model.Medico;
import com.br.consultas.model.Paciente;
import com.br.consultas.model.Pessoa;

@Named("agendamentoBean")
@ViewScoped
public class AgendamentoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Agendamento agendamento;
    private List<Agendamento> agendamentos;

    // listas usadas pelos modais - retornam PESSOA (root) para exibição
    private List<Pessoa> pacientes; 
    private List<Pessoa> medicos;

    // selecionados (exibição)
    private Pessoa pacientePessoa;
    private Pessoa medicoPessoa;

    // entidades reais que devem ser persistidas no agendamento
    private Paciente pacienteEntity;
    private Medico medicoEntity;

    // controllers
    private AgendamentoController agendamentoController = new AgendamentoController();
    private PessoaController pessoaController = new PessoaController();
    private PacienteController pacienteController = new PacienteController();
    private MedicoController medicoController = new MedicoController();
    
    private String data; // do datePicker de data
    private String hora; // do datePicker de hora

    @PostConstruct
    public void init() {
        agendamento = new Agendamento();

        // carregar listagens (métodos que você já tem)
        agendamentos = agendamentoController.listarAgendamentos();
        if (agendamentos == null) agendamentos = new ArrayList<>();
        else agendamentos.removeIf(a -> a == null);

        // lista de pessoas que têm role PACIENTE e MEDICO (para modal)
        pacientes = pessoaController.listarPessoasPorTipo("PACIENTE");
        if (pacientes == null) pacientes = new ArrayList<>();

        medicos = pessoaController.listarPessoasPorTipo("MEDICO");
        if (medicos == null) medicos = new ArrayList<>();
    }

    // Abre novo (segue padrão dos outros beans)
    public String novo() {
        agendamento = new Agendamento();
        pacientePessoa = null; medicoPessoa = null;
        pacienteEntity = null; medicoEntity = null;
        return "/views/pages/agendamentos/novo.xhtml?faces-redirect=true";
    }

    // Seleciona paciente a partir da linha Pessoa do modal
    public String selecionarPaciente(Pessoa p) {
        this.pacientePessoa = p;

        Long idPaciente = null;
        if (p != null && p.getIdsRoles() != null) {
            idPaciente = p.getIdsRoles().get("PACIENTE");
        }

        if (idPaciente != null) {
            this.pacienteEntity = pacienteController.buscarPacientePorId(idPaciente);
            // garante que o agendamento tenha a entidade correta
            agendamento.setPaciente(this.pacienteEntity);
        } else {
            this.pacienteEntity = null;
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Paciente sem role PACIENTE", null));
        }
        return null; // permanece na mesma página (re-render)
    }

    // Seleciona medico a partir da linha Pessoa do modal
    public String selecionarMedico(Pessoa m) {
        this.medicoPessoa = m;

        Long idMedico = null;
        if (m != null && m.getIdsRoles() != null) {
            idMedico = m.getIdsRoles().get("MEDICO");
        }

        if (idMedico != null) {
            this.medicoEntity = medicoController.buscarMedicoPorId(idMedico);
            agendamento.setMedico(this.medicoEntity);
        } else {
            this.medicoEntity = null;
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Pessoa não é um médico válido", null));
        }
        return null;
    }

    // Salvar novo agendamento (garante que entidades certas estejam setadas)
    public String salvar() {
    	
        FacesContext ctx = FacesContext.getCurrentInstance();

        // tenta preencher entidades caso o usuário tenha fechado o modal sem clicar em 'Selecionar'
        if (pacienteEntity == null && pacientePessoa != null && pacientePessoa.getIdsRoles() != null) {
            Long idPaciente = pacientePessoa.getIdsRoles().get("PACIENTE");
            if (idPaciente != null) pacienteEntity = pacienteController.buscarPacientePorId(idPaciente);
        }
        if (medicoEntity == null && medicoPessoa != null && medicoPessoa.getIdsRoles() != null) {
            Long idMedico = medicoPessoa.getIdsRoles().get("MEDICO");
            if (idMedico != null) medicoEntity = medicoController.buscarMedicoPorId(idMedico);
        }

        if (pacienteEntity == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um paciente.", null));
            return null;
        }
        if (medicoEntity == null) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um médico.", null));
            return null;
        }

        // garante que o agendamento tenha as entidades corretas antes de persistir
        agendamento.setPaciente(pacienteEntity);
        agendamento.setMedico(medicoEntity);
        
        // parse tolerante: tenta ISO (YYYY-MM-DD) e depois dd/MM/yyyy
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(data); // HTML5 date => "yyyy-MM-dd"
        } catch (DateTimeParseException ex) {
            try {
                localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException ex2) {
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de data inválido", "Use dd/MM/yyyy ou selecione no calendário"));
                return null;
            }
        }

        // hora: espera "HH:mm"
        LocalTime localTime = null;
        try {
            localTime = LocalTime.parse(hora); // "HH:mm"
        } catch (DateTimeParseException ex) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de hora inválido", "Use HH:mm"));
            return null;
        }

        LocalDateTime dataHora = LocalDateTime.of(localDate, localTime);
        agendamento.setDataHora(dataHora);

        // persiste
        agendamentoController.salvarAgendamento(agendamento);

        // reset
        agendamento = new Agendamento();
        pacientePessoa = null; medicoPessoa = null;
        pacienteEntity = null; medicoEntity = null;

        return "/views/pages/agendamentos.xhtml?faces-redirect=true";
    }
    
    public String getNomePaciente(Agendamento ag) {
        if (ag == null || ag.getPaciente() == null) return "";

        Long idPaciente = ag.getPaciente().getId(); // ou outro getter que você tenha
        for (Pessoa p : pacientes) {
            if (p.getIdsRoles() != null && idPaciente.equals(p.getIdsRoles().get("PACIENTE"))) {
                return p.getNome();
            }
        }
        return "";
    }

    public String getNomeMedico(Agendamento ag) {
        if (ag == null || ag.getMedico() == null) return "";

        Long idMedico = ag.getMedico().getId();
        for (Pessoa p : medicos) {
            if (p.getIdsRoles() != null && idMedico.equals(p.getIdsRoles().get("MEDICO"))) {
                return p.getNome();
            }
        }
        return "";
    }
    
    // Converte o LocalDateTime do agendamento atual para string no formato yyyy-MM-dd (HTML5 input date)
    public String getDataString() {
        if (agendamento == null || agendamento.getDataHora() == null) return "";
        return agendamento.getDataHora().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // Converte o LocalDateTime do agendamento atual para string no formato HH:mm (HTML5 input time)
    public String getHoraString() {
        if (agendamento == null || agendamento.getDataHora() == null) return "";
        return agendamento.getDataHora().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    // Se quiser preencher os campos de edição de uma lista, pode criar métodos que recebem o agendamento como parâmetro
    public String getDataString(Agendamento ag) {
        if (ag == null || ag.getDataHora() == null) return "";
        return ag.getDataHora().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getHoraString(Agendamento ag) {
        if (ag == null || ag.getDataHora() == null) return "";
        return ag.getDataHora().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }


    // getters / setters (usados pela view)
    public Agendamento getAgendamento() { return agendamento; }
    public void setAgendamento(Agendamento agendamento) { this.agendamento = agendamento; }

    public List<Agendamento> getAgendamentos() { return agendamentos; }

    public List<Pessoa> getPacientes() { return pacientes; }
    public List<Pessoa> getMedicos() { return medicos; }

    public Pessoa getPacientePessoa() { return pacientePessoa; }
    public Pessoa getMedicoPessoa() { return medicoPessoa; }

    public Paciente getPacienteEntity() { return pacienteEntity; }
    public Medico getMedicoEntity() { return medicoEntity; }

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}
