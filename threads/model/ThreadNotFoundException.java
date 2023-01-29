package io.blacktoast.utils.threads.model;

import ar.org.blacktoast.hilos.model.exceptions.AppException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ThreadNotFoundException extends AppException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7505233049280195362L;
	
	public ThreadNotFoundException() {
		this.setMessage("Thread no encontrado!!");
		this.setCode(ThreadErrors.NOT_FOUND);
	}
	
	
}
