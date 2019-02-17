package repository;

import domain.MessageTask;
import domain.ValidationException;
import domain.Validator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class MessageTaskFileRepository extends AbstractFileRepository<String,MessageTask> {

    public MessageTaskFileRepository(Validator<MessageTask> vali, String fileName) {
        super(vali,fileName);

    }

    @Override
    MessageTask buildEntity(String[] f) {
        MessageTask m;
        LocalDateTime d=LocalDateTime.parse(f[5],MessageTask.getDateFormatter());
        return new MessageTask(f[0],f[1],f[2],f[3], f[4], d);
    }
}
