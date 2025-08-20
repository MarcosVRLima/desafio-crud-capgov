package com.br.consultas.teste;

import com.br.consultas.dao.PacienteDAO;
import com.br.consultas.dao.MedicoDAO;
import com.br.consultas.dao.ConsultaDAO;
import com.br.consultas.model.Paciente;
import com.br.consultas.model.Medico;
import com.br.consultas.model.Consulta;

import java.time.LocalDateTime;

public class MainTest {

    public static void main(String[] args) {

        // Criar DAOs
        PacienteDAO pacienteDAO = new PacienteDAO();
        MedicoDAO medicoDAO = new MedicoDAO();
        ConsultaDAO consultaDAO = new ConsultaDAO();

        // Criar um paciente
        Paciente paciente = new Paciente();
        paciente.setNome("João Silva");
        paciente.setCpf("123.456.789-00");
        paciente.setTelefone("(85) 99999-0000");
        pacienteDAO.save(paciente);

        // Criar um médico
        Medico medico = new Medico();
        medico.setNome("Dra. Maria Souza");
        medico.setCpf("987.654.321-00");
        medico.setTelefone("(85) 98888-1111");
        medico.setEspecialidade("Cardiologia");
        medicoDAO.save(medico);

        // Criar uma consulta
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(LocalDateTime.of(2025, 8, 25, 14, 30));
        consulta.setStatus("Agendada");
        consultaDAO.save(consulta);

        System.out.println("Teste concluído: Paciente, Médico e Consulta salvos com sucesso!");
    }
}
