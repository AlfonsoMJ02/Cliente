package com.digis01.AMorenoProgramacionNCapasMaven.ML;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
   private int IdUsuario;
    private String Nombre;
    private String ApellidoPaterno;
    private String ApellidoMaterno;   
    private String Email;
    private String Password;
    private String Sexo;
    private String Telefono;
    private String Celular;
    private String Curp;
    private String UserName;
    private LocalDate FechaNacimiento;
    public String Imagen;
    private int Estatus;

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int Estatus) {
        this.Estatus = Estatus;
    }
 
    private Rol Rol;
   
    private Pais pais;
    
    private Estado estado;
    
    private Municipio municipio;
    
    private Colonia colonia;
    
    private Direccion direccion;
    
    private List<Direccion> Direcciones;
    
    public Colonia getColonia() {
        return colonia;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Usuario() {
        Direcciones = new ArrayList<>();
        Rol = new com.digis01.AMorenoProgramacionNCapasMaven.ML.Rol();
    }
    
    public int getIdUsuario(){
       return IdUsuario;
    }
    
    public void setIdUsuario(int IdUsuario){
        this.IdUsuario = IdUsuario;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getApellidoPaterno(){
        return ApellidoPaterno;
    }
    
    public void setApellidoPaterno(String ApellidoPaterno){
        this.ApellidoPaterno = ApellidoPaterno;
    }
    
    public String getApellidoMaterno(){
        return ApellidoMaterno;
    }
    
    public void setApellidoMaterno(String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public String getPassword(){
        return Password;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    
    public String getSexo(){
        return Sexo;
    }
    
    public void setSexo(String Sexo){
        this.Sexo = Sexo;
    }
    
    public String getTelefono(){
        return Telefono;
    }
    
    public void setTelefono(String Telefono){
        this.Telefono = Telefono;
    }
    
    public String getCelular(){
        return Celular;
    }
    
    public void setCelular(String Celular){
        this.Celular = Celular;
    }
    
    public String getCurp(){
        return Curp;
    }
    
    public void setCurp(String Curp){
        this.Curp = Curp;
    }
    
    public String getUserName(){
        return UserName;
    }
    
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    
    public LocalDate getFechaNacimiento(){
        return FechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate FechaNacimiento){
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }
    
    
    
    public Direccion getdireccion(){
        return direccion;
    }
    
    public void setdireccion(Direccion direccion){
        this.direccion = direccion;
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }
    
    
}
