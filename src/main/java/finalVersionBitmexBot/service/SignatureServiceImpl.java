package finalVersionBitmexBot.service;

import finalVersionBitmexBot.model.Signature;

public class SignatureServiceImpl implements SignatureService{
    private Signature signature;

    @Override
    public String createSignature() {
        generateSignature();
        // тут создаем подпимсь и передаем ее в клиента
        return "";
    }

    private void generateSignature() {
    }
}
