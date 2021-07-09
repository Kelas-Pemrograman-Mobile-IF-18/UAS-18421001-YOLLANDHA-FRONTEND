package com.yolla.apkbus.server;

public class BaseURL {

    public static String baseURL = "http://192.168.45.240:5050/";

    public static String login = baseURL + "user/login";
    public static String register = baseURL + "user/registrasi";

    // bus
    public static String dataBus = baseURL + "bus/dataBus";
    public static String editDataBus = baseURL + "bus/ubah/";
    public static String editDataGambarBus = baseURL + "bus/ubahgambar/";
    public static String hapusData = baseURL + "bus/hapus/";
    public static String inputBus = baseURL + "bus/input";

    //cart
    public static String insertCart = baseURL+ "cart/list";
    public static String dataCart = baseURL+ "cart/dataCart/";
    public static String dataCartaja = baseURL+ "cart/dataCartaja/";
    public static String hapusCart = baseURL+ "cart/hapusCart";

    // transaksi
    public static String dataTransaksi = baseURL + "transaksi/dataTransaksi";
    public static String hapusTransaksi = baseURL+ "transaksi/hapusTransaksi";
    public static String ubahTransaksi = baseURL +"transaks/ubahTransaksi";
    public static String ubahBUktiTF = baseURL + "transaksi/ubahBuktiTF";
}