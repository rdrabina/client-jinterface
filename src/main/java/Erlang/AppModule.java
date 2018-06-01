package Erlang;

import com.ericsson.otp.erlang.*;

import java.io.IOException;

public class AppModule {
    //OtpMbox mbox;
    //OtpNode otpNode;

    private String nodeClient = "client";
    private String nodeServer = "server@Radzio"; //tutaj trzeba zmienic nazwe serwera
    private String cookie = "mycookie";
    private String nameOfProcess = "moduleName";
    boolean isReceivedSuccess = false;



    /*public AppModule(){
        try {
            otpNode = new OtpNode(node,cookie);
            mbox = otpNode.createMbox();
            System.out.println(mbox.self());
        } catch (IOException e) {
            System.out.println("Server did not respond.");
        }
    }*/

    private OtpSelf client;
    private OtpPeer server;
    private OtpConnection connection;

    public AppModule() {
        try {
            client = new OtpSelf(nodeClient, cookie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server = new OtpPeer(nodeServer);

        try {
            connection = client.connect(server);
        } catch (IOException e) {
            System.out.println("Problems with connection");
        } catch (OtpAuthException e) {
            System.out.println("Problems with authorization");
        }
    }

    public boolean isReceivedSuccess() {
        return isReceivedSuccess;
    }

    public void sendData(OtpErlangTuple tuple) {
        try {
            connection.send(nameOfProcess, tuple);
            // System.out.println(response);
        } catch (IOException e) {
            System.out.println("Some problem occured during sending data");
        }
    }

    public OtpErlangObject receiveReply() {
        OtpErlangObject reply = null;
        try {
            reply = connection.receive();

        } catch (IOException e) {
            System.out.println("Some problems during receiving login answer occured");
        } catch (OtpAuthException e) {
            System.out.println("Problems with authorization occured");
        } catch (OtpErlangExit otpErlangExit1) {
            System.out.println("Problems during receiving login answer with exit occured");
        }
        return reply;
    }

    public String getStringFromTuple(OtpErlangObject otpErlangObject) {
        if (otpErlangObject instanceof OtpErlangString) {
            if (!((OtpErlangString) otpErlangObject).stringValue().isEmpty())
                throw new IllegalArgumentException("Received no data");
            else
                return ((OtpErlangString) otpErlangObject).stringValue();

        }
        return null;
    }

    public void setIsReceivedSuccess(OtpErlangObject reply) {
        OtpErlangTuple receivedMessage;

        if (reply instanceof OtpErlangTuple) {
            receivedMessage = (OtpErlangTuple) reply;
            if (receivedMessage.elementAt(0).equals(new OtpErlangAtom("ok"))) {
                isReceivedSuccess = true;
            }
        }
    }

    public void receiveIsSuccess() {
        OtpErlangObject reply = receiveReply();
        setIsReceivedSuccess(reply);
    }

    public String receiveString() {
        OtpErlangObject reply = receiveReply();
        setIsReceivedSuccess(reply);
        OtpErlangTuple receivedMessage = null;

        if (isReceivedSuccess) {
            OtpErlangObject otpErlangObject = receivedMessage.elementAt(1);
            return getStringFromTuple(otpErlangObject);
        }
        return null;
    }

    public String receiveNameAndSurname() {
        OtpErlangObject reply = receiveReply();
        setIsReceivedSuccess(reply);
        OtpErlangTuple receivedMessage = null;

        if (isReceivedSuccess) {
            OtpErlangObject otpErlangObject = receivedMessage.elementAt(1);
            OtpErlangObject otpErlangObject1 = receivedMessage.elementAt(2);
            return getStringFromTuple(otpErlangObject) + " " + getStringFromTuple(otpErlangObject1);
        }
        return null;
    }

    public void sendRegistrationData(String name, String surname, String password, String eMail, String address, String phoneNumber, String question, String answer) {
        OtpErlangObject[] message = new OtpErlangObject[10];
        message[0] = new OtpErlangAtom("register");
        message[1] = client.pid();
        message[2] = new OtpErlangString(name);
        message[3] = new OtpErlangString(surname);
        message[4] = new OtpErlangString(password);
        message[5] = new OtpErlangString(eMail);
        message[6] = new OtpErlangString(address);
        message[7] = new OtpErlangString(phoneNumber);
        message[8] = new OtpErlangString(question);
        message[9] = new OtpErlangString(answer);

        OtpErlangTuple otpErlangTuple = new OtpErlangTuple(message);
        sendData(otpErlangTuple);
        // mbox.send("RegistrationSend",node,otpErlangTuple);
    }

    public void sendLoginData(String login, String password) {
        OtpErlangObject[] message = new OtpErlangObject[4];
        message[0] = new OtpErlangAtom("login");
        message[1] = client.pid();
        message[2] = new OtpErlangString(login);
        message[3] = new OtpErlangString(password);

        OtpErlangTuple otpErlangTuple = new OtpErlangTuple(message);
        sendData(otpErlangTuple);
    }

    public void sendLeadingQuestionQuery(String eMail) {
        OtpErlangObject[] message = new OtpErlangObject[3];
        message[0] = new OtpErlangAtom("emergency_question");
        message[1] = client.pid();
        message[2] = new OtpErlangString(eMail);

        OtpErlangTuple otpErlangTuple = new OtpErlangTuple(message);
        sendData(otpErlangTuple);
    }

    public void sendAnswer(String answer) {
        OtpErlangObject[] message = new OtpErlangObject[3];
        message[0] = new OtpErlangAtom("answer_verification");
        message[1] = client.pid();
        message[2] = new OtpErlangString(answer);

        OtpErlangTuple otpErlangTuple = new OtpErlangTuple(message);
        sendData(otpErlangTuple);
    }

    public void sendPasswordChangerData(String eMail, String password, String token) {
        OtpErlangObject[] message = new OtpErlangObject[5];
        message[0] = new OtpErlangAtom("change_password");
        message[1] = client.pid();
        message[2] = new OtpErlangString(eMail);
        message[3] = new OtpErlangString(password);
        message[4] = new OtpErlangString(token);

        OtpErlangTuple otpErlangTuple = new OtpErlangTuple(message);
        sendData(otpErlangTuple);
    }

    public void sendNameAndSurnameQuery(String eMail, String token) {
        OtpErlangObject[] message = new OtpErlangObject[4];
        message[0] = new OtpErlangAtom("get_info");
        message[1] = client.pid();
        message[2] = new OtpErlangString(eMail);
        message[3] = new OtpErlangString(token);

        OtpErlangTuple otpErlangTuple = new OtpErlangTuple(message);
        sendData(otpErlangTuple);
    }
}












/*

    public void sendLogin(String login) {
        OtpErlangObject[] message = new OtpErlangObject[2];
        message[0] = client.pid();
        message[1] = new OtpErlangString(login);

        OtpErlangTuple otpErlangTuple = new OtpErlangTuple(message);
        mbox.send("LoginSend",node,otpErlangTuple);


    }


    //public boolean isReceivedSuccess() {
    {OtpErlangObject reply = null;
        OtpErlangTuple receivedMessage;
        try {
            reply = mbox.receive();
        } catch (OtpErlangExit otpErlangExit) {
            System.out.println("Some problems with receiving occured.");
        } catch (OtpErlangDecodeException e) {
            System.out.println("Some problems with receiving occured.");
        }
        if (reply instanceof OtpErlangTuple){
            receivedMessage=(OtpErlangTuple) reply;
            if (receivedMessage.elementAt(0).equals(new OtpErlangAtom("ok"))) return true;
            else return false;
        }
        return false;
        //return true; //na potrzeby testu
    }

   // public String reveiveLeadingQuestion() {
   {OtpErlangObject reply = null;
        OtpErlangTuple receivedMessage;
        try {
            reply = mbox.receive();
        } catch (OtpErlangExit otpErlangExit) {
            System.out.println("Some problems with receiving occured.");
        } catch (OtpErlangDecodeException e) {
            System.out.println("Some problems with receiving occured.");
        }
        if (reply instanceof OtpErlangTuple){
            receivedMessage=(OtpErlangTuple) reply;
            if (receivedMessage.elementAt(0).equals(new OtpErlangAtom("ok"))){
                OtpErlangObject question = receivedMessage.elementAt(1);

                if(question instanceof OtpErlangString){
                    if (!((OtpErlangString) question).stringValue().isEmpty())
                        throw new IllegalArgumentException("Received no question");
                    else
                        return ((OtpErlangString) question).stringValue();
                }
            }
            else return null;
        }
        return null;
        //return "hehe?" //na potrzeby testu
    }

    //public String receiveNameAndSurname() {
    {  OtpErlangObject reply = null;
        OtpErlangTuple receivedMessage;
        try {
            reply = mbox.receive();
        } catch (OtpErlangExit otpErlangExit) {
            System.out.println("Some problems with receiving occured.");
        } catch (OtpErlangDecodeException e) {
            System.out.println("Some problems with receiving occured.");
        }
        if (reply instanceof OtpErlangTuple){
            receivedMessage=(OtpErlangTuple) reply;
            if (receivedMessage.elementAt(0).equals(new OtpErlangAtom("ok"))){
                OtpErlangObject name = receivedMessage.elementAt(1);
                OtpErlangObject surname = receivedMessage.elementAt(2);

                if(name instanceof OtpErlangString && surname instanceof OtpErlangString){
                    if (!((OtpErlangString) name).stringValue().isEmpty() && !((OtpErlangString) surname).stringValue().isEmpty())
                        throw new IllegalArgumentException("Received no name or no surname");
                    else
                        return ((OtpErlangString) name).stringValue()+" "+((OtpErlangString) surname).stringValue();
                }
            }
            else return null;

        }
        return null;
        //retrun "Byczek Fernando" //na potrzeby testu
    }

}

*/