package lab8.client.presenter.commands;

import lab7.serializedobjects.MessagesType;
import lab7.serializedobjects.ServerMessage;
import lab7.serializedobjects.dataclasses.SpaceMarine;
import lab8.client.Main;
import lab8.client.presenter.Client;
import lab8.client.presenter.Presenter;
import lab8.client.presenter.TCPStreamClient;
import lab8.client.presenter.exceptions.CommandArgNotFound;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FetchCommand extends AbstractNetworkCommand {

    private SpaceMarine[] fetchedData;

    @Override
    public void execute(Presenter presenter) {
        Client client = new TCPStreamClient(Main.SERVER_HOSTNAME, Main.SERVER_PORT);
        try {
            client.initSocket();
            sendObject(client.getOutputStream(), prepareForSerialization(presenter.getUser()));
            Object answer = getAnswer(client.getInputStream());
            processAnswer(answer, presenter);
        } catch (IOException e) {
            if (presenter.getView() != null)
                presenter.getView().showError(e.getLocalizedMessage());
        } catch (NullPointerException ignored) {

        }
    }

    @Override
    protected void processServerMessages(List<ServerMessage> messages, Presenter presenter) {
        ServerMessage message = messages.get(0);
        if (message.getType() == MessagesType.RESULT) {
            Object[] rawData = message.getData();
            this.fetchedData = Arrays.copyOf(rawData, rawData.length, SpaceMarine[].class);
        } else {
            super.processServerMessages(messages, presenter);
        }
    }

    public SpaceMarine[] getFetchedData() {
        return this.fetchedData;
    }

    @Override
    public String getName() {
        return "fetch";
    }

    @Override
    public String getDescription() {
        return "получить коллекцию с сервера";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) {
        return;
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
