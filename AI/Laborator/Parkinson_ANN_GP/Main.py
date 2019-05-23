from UI import UI

def main():
    # ui = UI('date/file_train', 'date/file_test', 'date/file_result')
    # ui = UI('date/easy_parkinson_02_train', 'date/easy_parkinson_02_test', 'date/file_result')
    # ui = UI('date/medium_parkinson_02_train', 'date/medium_parkinson_02_test', 'date/file_result')
    ui = UI('date/hard_parkinson_02_train', 'date/hard_parkinson_02_test', 'date/file_result')
    ui.run()

main()
