package lobby;

import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.JsonObject;

import juego.personas.Jugador;

public class Usuario {
	private int id;
	private String username;
	private String password;
	private ArrayList<Partida> partidasJugadas;
	private int puntaje;
	private Sala sala;
	private Jugador jugador;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public Usuario(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Usuario(JsonObject jsonObject) {
		this.id = Integer.valueOf(jsonObject.get("id").toString());
		this.username = jsonObject.get("username").toString();
		this.password = jsonObject.get("password").toString();
	}

	public Usuario(int id) {
		this.id = id;
	}

	public ArrayList<Partida> getPartidasJugadas() {
		return partidasJugadas;
	}

	public void setPartidasJugadas(ArrayList<Partida> partidasJugadas) {
		this.partidasJugadas = partidasJugadas;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Sala crearSala() {
		Scanner lector = new Scanner(System.in);
		String nombreSala = "sala"; // para simplificar ahora le asigno un nombre luego hay que leer de consola
		String password = "1234";
		int cantDeUsrMaximos = 2; // mas adelante se podra elegir

//		// Voy ingresando los parametros
//		System.out.println("Ingrese el nombre de la sala: ");
//		nombreSala = lector.nextLine();
//		// Voy ingresando la pw, PUEDE SER VACIA
//		System.out.println("Ingrese la contrase�a de la sala: ");
//		password = lector.nextLine();
//
//		do {
//			// Por ahora salas entre 2 y 5
//			System.out.println("Ingrese la cantidad maxima de usuarios: ");
//			cantDeUsrMaximos = lector.nextInt();
//		} while (cantDeUsrMaximos < 2 && cantDeUsrMaximos > 5);
//
//	lector.close();
		// Creo la sala
		Sala sala = new Sala(nombreSala, password, cantDeUsrMaximos, this);
		// Conecto al usuario a la misma
		conectarseALaSala(sala);
		// lector.close();
		return sala;
	}

	public boolean conectarseALaSala(Sala sala) {
		if (sala.getCapacidadActual() < sala.getCapacidadMaxima()) {
			this.setSala(sala);
			sala.setUsuariosActivos(this);
			sala.setCapacidadActual(sala.getCapacidadActual() + 1);
			return true;
		}
		sala.setSalaLlena(true);
		return false;
	}

	public void salirDeSala() {
		/*
		 * Si no lo saco, queda el usuario sin sala, pero figura como usuario activo, en
		 * al sala
		 */
		if (this.jugador != null) {
			this.sala.getJugadoresActivos().remove(this.jugador);
			this.setJugador(null);
		}
		this.sala.getUsuariosActivos().remove(this);
		this.sala = null;
	}

	public Jugador getJugador() {
		if (this.jugador != null) {
			return this.jugador;
		}

		if (this.sala != null && this.sala.getPartidaActual() != null
				&& this.sala.getPartidaActual().getUsuariosActivosEnSala() != null) {
			for (Usuario user : this.sala.getPartidaActual().getUsuariosActivosEnSala()) {
				if (user == this) {
					return user.getJugador();
				}
			}
		}
		return null;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
}