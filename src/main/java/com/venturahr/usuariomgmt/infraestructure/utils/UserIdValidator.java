package com.venturahr.usuariomgmt.infraestructure.utils;

public class UserIdValidator {

    public static boolean isValidCpfFormat(String cpf){
        if(cpf == null){
            return false;
        }
        return cpf.matches("^[0-9]{11}$");
    }

    public static boolean isValidCnpjFormat(String cnpj){
        if(cnpj == null){
            return false;
        }
        return cnpj.matches("^[0-9]{14}$");
    }
}
