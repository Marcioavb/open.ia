package com.wacanda.open.ia.controller;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ia")
public class SampleController {

    @Autowired
    private OpenAiChatClient client;

    @GetMapping("/simples")
    public String primeiroTeste(
            @RequestParam(value="msg", defaultValue= "quais estados do Brasil")
             String msg
    ){
        return client.call(msg);

    }

    @GetMapping("/teste")
    public ChatResponse segundoTeste(
            @RequestParam(value="msg", defaultValue= "quais estados do Brasil")
            String msg
    ){
        return client.call(new Prompt(msg));

    }

    @GetMapping("/estado")
    public String buscaEstadosBrasil(
            @RequestParam(value="msg", defaultValue= "quais estados do Brasil")
            String msg
    ){
        PromptTemplate prompt = new PromptTemplate(
                """
                        voce só pode responder perguntas sobre estados do Brasil,
                        caso receber alguma pergunta não relacionada, responder:
                        [Opa, Opa, Opa, aqui e só sobre estados.]
                        Por favor retorne o {msg} enumerado com as capitais
                        """
        );
        prompt.add("msg", msg);

        return client.call(prompt.create()).getResult().getOutput().getContent();
    }
}
