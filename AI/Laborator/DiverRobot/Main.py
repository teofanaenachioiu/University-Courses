from UI import UI


def main():
    # fileTrain = "example_robot_01_train"
    # fileTest = "example_robot_01_test"
    # fileTrain = "example_drive_01_train"
    # fileTest = "example_drive_01_test"
    fileTrain = "hard_drive_02_train.txt"
    fileTest = "hard_drive_02_test.txt"
    # fileTrain = "medium_drive_01_train.txt"
    # fileTest ="medium_drive_01_test.txt"
    # fileTrain = "easy_drive_01_train"
    # fileTest = "easy_drive_01_test"
    ui = UI(fileTrain, fileTest)
    ui.run()


main()
