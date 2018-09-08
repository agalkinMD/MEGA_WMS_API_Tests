package ru.mobiledimension.megaapp.wms.utilities;

import ru.mobiledimension.megaapp.wms.models.Purchase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    static String lastAddedBarcode;

    static final String actualBarcodeListFilePath = "src/test/resources/barcodes/BarcodeList_actual.txt";
    static final String verifiedBarcodeListFilePath = "src/test/resources/barcodes/BarcodeList_verified.txt";
    static final String unregisteredBarcodeListFilePath = "src/test/resources/barcodes/BarcodeList_unregistered.txt";

//    Этот блок методов необходим для получения ссылки на экземпляр файла, содержащего списки
//    только что полученных, верифицированных или еще незарегистрированных кодов
    public static File getActualBarcodeListFile() {
        return new File(actualBarcodeListFilePath);
    }
    public static File getVerifiedBarcodeListFile() {
        return new File(verifiedBarcodeListFilePath);
    }
    public static File getUnregisteredBarcodeListFile() {
        return new File(unregisteredBarcodeListFilePath);
    }

//    Этот блок методов необходим для получения и сохранения только что добавленной покупки
    public static String getLastAddedBarcode() {
        return lastAddedBarcode;
    }
    public static void setLastAddedBarcode(String barcode) {
        lastAddedBarcode = barcode;
    }

//    Этот блок методов необходим для работы с данными файлов: чтением и записью, копированием и печатью на консоль,
//    а также получением еще незарегистрированного кода
    public static ArrayList<String> readBarcodeList(File file) {
        String sCurrentLine;
        ArrayList<String> barcodeList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((sCurrentLine = reader.readLine()) != null) {
                barcodeList.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return barcodeList;
    }
    public static void writeBarcodeList(List<Purchase> purchases, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Purchase purchase : purchases) {
                writer.write(purchase.getBarcode() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeBarcodeList(String barcode, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(barcode + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void copyBarcodeListToFile(File sourceFile, File destinationFile) {
        String barcode;

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile))) {
                while ((barcode = reader.readLine()) != null) {
                    writer.write(barcode + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printBarcodeList(File file) {
        String sCurrentLine;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((sCurrentLine = reader.readLine()) != null)
                System.out.println(sCurrentLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String readLastUnregisteredBarcode() {
        int lineAmount = 0;
        String sCurrentLine;

        String barcode = new String();

        try (BufferedReader reader = new BufferedReader(new FileReader(getUnregisteredBarcodeListFile()))) {
            while ((sCurrentLine = reader.readLine()) != null) {
                lineAmount++;
                barcode = sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lineAmount == 0) {
            System.out.println("No available unregistered barcodes!\n");
            return null;
        }

        lastAddedBarcode = barcode;

        return barcode;
    }

//    Этот блок методов является вспомогательным. Методы позволяют проверить наличие содержимого в файле,
//    его эквивалентность с содержимым другого файла, а также удалить последню строку в файле
    public static boolean isEqualContentInActualAndVerifiedFiles(File actualFile, File verifiedFile) {
        boolean isEqualContent = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(getVerifiedBarcodeListFile()))) {
            if (reader.readLine() == null) {
                System.out.println("There's no initial checks because empty \"PurchasesList_verified\" file. " +
                        "It happens only once due to first test suite execution.\n");
                return true;
            } else {
                if (!isEqualContent(actualFile, verifiedFile)) {
                    System.out.println("Content in files \"PurchasesList_actual\" and \"PurchasesList_verified\" doesn't match!");

                    System.out.println("Barcode actual list:");
                    printBarcodeList(getActualBarcodeListFile());

                    System.out.println("Barcode verified list:");
                    printBarcodeList(getVerifiedBarcodeListFile());

                    isEqualContent = false;
                } else {
                    System.out.println("Barcode actual list:");
                    printBarcodeList(getActualBarcodeListFile());

                    System.out.println("Barcode verified list:");
                    printBarcodeList(getVerifiedBarcodeListFile());

                    isEqualContent = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isEqualContent;
    }
    public static boolean isEqualContent(File firstFile, File secondFile) {
        String sCurrentLine;

        ArrayList<String> barcodeListFromFirstFile = new ArrayList<>();
        ArrayList<String> barcodeListFromSecondFile = new ArrayList<>();


        try (BufferedReader readerFirstFile = new BufferedReader(new FileReader(firstFile))) {
            while ((sCurrentLine = readerFirstFile.readLine()) != null) {
                barcodeListFromFirstFile.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader readerSecondFile = new BufferedReader(new FileReader(secondFile))) {
            while ((sCurrentLine = readerSecondFile.readLine()) != null) {
                barcodeListFromSecondFile.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> tmpList = new ArrayList<>(barcodeListFromFirstFile);
        tmpList.removeAll(barcodeListFromSecondFile);

        if (tmpList.size() == 0)
            return true;
        else return false;
    }
    public static boolean isEmpty(File file) {
        int linesAmount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null)
                linesAmount++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (linesAmount == 0)
            return true;
        else return false;
    }
    public static void deleteLastLine(File file) {
        StringBuilder builder = new StringBuilder();
        String sCurrentLine;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((sCurrentLine = reader.readLine()) != null)
                builder.append(sCurrentLine + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] lines = builder.toString().split("\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < lines.length - 1; i++) {
                writer.write(lines[i] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
