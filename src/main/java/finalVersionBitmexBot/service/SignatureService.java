package finalVersionBitmexBot.service;

public interface SignatureService {

    String createSignature(String verb, String url, String data);
    String createSignatureForOrdersList(String verb, String url, String data);
}
