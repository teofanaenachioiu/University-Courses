package com.socket.example;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ProblemsProtocol {
    private static final int WAITING = 0;
    private static final int USERNAME_REQUEST_SENT = 1;
    private static final int SENT_NUMBER_REQUEST = 2;
    private static final int SENT_PROBLEM = 3;

    private int state = WAITING;

    private String username;

    private Map<String, String> users;

    public ProblemsProtocol(Map<String, String> users) {
        this.users = users;
    }

    public String processInput(String theInput) {
        logMessage(theInput);
        String theOutput = null;

        switch (state) {
        case WAITING:
            if (username == null || username.length() <= 1) {
                theOutput = "Who's there? Identify yourself!";
                state = USERNAME_REQUEST_SENT;
            }
            break;
        case USERNAME_REQUEST_SENT:
            if (theInput.length() <= 1) {
                theOutput = "Who's there? Identify yourself!";
            }
            else {
                username = theInput;
                theOutput = "Hello " + username
                        + "! Welcome to your first networking exam! Please enter a number between "
                        + "1 and 6 which represents the number of problem you will have to solve!";
                state = SENT_NUMBER_REQUEST;
            }
            break;
        case SENT_NUMBER_REQUEST:
            if (theInput.equals("1") || theInput.equals("2") || theInput.equals("3")
                    || theInput.equals("4") || theInput.equals("5") || theInput.equals("6")) {

                if (this.users.containsKey(username.toLowerCase())) {
                    System.out.println("######################## WARNING ########################");
                    System.out.println("Intrusion detected! User: " + username);
                    System.out.println("######################## ####### ########################");

                    theOutput = "You thought there for a second that you get another chance!"
                            + " Solve the assigned problem! Bye!";
                    state = SENT_NUMBER_REQUEST;
                }
                else {
                    Integer problemNumber = ThreadLocalRandom.current().nextInt(0, 6);

                    theOutput = Problems.tcpProblems.get(problemNumber);
                    theOutput += " Are you satisfied with this problem? [y/n]";
                    addNewUserToMap(problemNumber);
                    state = SENT_PROBLEM;
                }

            }
            else {
                theOutput = "ERROR: The number you provided was not valid! Received number: '" + theInput + "'. Please enter a number between 1 and 5!";
                state = SENT_NUMBER_REQUEST;
            }
            break;
        case SENT_PROBLEM:
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Bye";
            }
            else {
                theOutput = "You thought there for a second that you get another chance!"
                        + " Solve the assigned problem! Bye!";
            }
            state = WAITING;
            break;
        default:
            theOutput = "Who's there? Identify yourself!";
            break;
        }

        return theOutput;
    }

    private void logMessage(String theInput) {
        System.out.println(
                "Received message: |" + theInput + "| from: '" + ((username != null) ?
                        username :
                        "") + "'");
    }

    private void addNewUserToMap(Integer problemNumber) {
        System.out.println("===================== Adding new user: " + this.username
                + " / " + String.valueOf(problemNumber) + " ====================");

        users.put(username.toLowerCase(), String.valueOf(problemNumber));

        for (String key : this.users.keySet()) {
            System.out.println("User: " + key + ": Problem -> " + users.get(key));
        }
        System.out.println(
                "==========================================================================");
    }
}