
package co.edu.unicauca.distribuidos.core.capaControladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.DeporteRespuestaDTO;
import co.edu.unicauca.distribuidos.core.fachadaServices.services.IDeporteService;

@RestController /*Crear na serie de metodos que ofrece servicios rest */
@RequestMapping("/api") /*Toda URL para ofrecer esos servicios web, debe empezar con /api */
@CrossOrigin(origins = "http://localhost:4200",  
methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}) 
public class DeporteRestController {

	@Autowired
	private IDeporteService clienteService; /*Inyeccion de un objeto que corresponde a la fachada 
											Esta debe tener la mayor responsabilidad para dejar mas limpio el contorlador*/

	@GetMapping("/clientes") 
	public List<ClienteDTO> listarClientes() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/{id}")/*Tambien se puede enviar este parametro con la anotacion @RequestParam */
	public ClienteDTO consultarCliente(@PathVariable Integer id) {
		ClienteDTO objCliente = null;
		objCliente = clienteService.findById(id);
		return objCliente;
	}

	
	@GetMapping("/clientes2")
	public ClienteDTO consultarCliente2(@RequestParam Integer id) {
		ClienteDTO objCliente = null;
		objCliente = clienteService.findById(id);
		return objCliente;
	}

	@GetMapping("clientes2/{name}/{age}")
	public String getMessage(@PathVariable String name,
			@PathVariable("age") String edad) {
		String msg = String.format("%s es %s años viejo", name, edad);
		System.out.println(msg);
		return msg;
	}

	@GetMapping("consultarClientes")
	public String consultarClientesConVariosParametros(@RequestParam String nombres,
			@RequestParam String apellidos) {
		String msg = String.format("buscando un cliente por nombre: %s, apellidos: %s", nombres, apellidos);
		System.out.println(msg);
		return msg;
	}

	@PostMapping("/clientes")
	public ClienteDTO crearCliente(@RequestBody ClienteDTO cliente) {/*Se hace la desconversion de JSON con esa anotacion */
		ClienteDTO objCliente = null;
		objCliente = clienteService.save(cliente);
		return objCliente;
	}

	@PutMapping("/clientes/{id}")
	public ClienteDTO actualizarCliente(@RequestBody ClienteDTO cliente, @PathVariable Integer id) {
		ClienteDTO objCliente = null;
		ClienteDTO clienteActual = clienteService.findById(id);
		if (clienteActual != null) {
			objCliente = clienteService.update(id, cliente);
		}
		return objCliente;
	}

	@DeleteMapping("/clientes/{id}")
	public Boolean eliminarCliente(@PathVariable Integer id) {
		Boolean bandera = false;
		ClienteDTO clienteActual = clienteService.findById(id);
		if (clienteActual != null) {
			bandera = clienteService.delete(id);
		}
		return bandera;
	}

	@GetMapping("/clientes/listarCabeceras")
	public void listarCabeceras(@RequestHeader Map<String, String> headers) {
		System.out.println("cabeceras");
		headers.forEach((key, value) -> {
			System.out.println(String.format("Cabecera '%s' = %s", key, value));
		});
	}

}