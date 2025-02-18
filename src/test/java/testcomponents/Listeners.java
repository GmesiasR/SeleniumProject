package testcomponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import resources.ExtentReporterNG;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal <ExtentTest>extentTest = new ThreadLocal(); //ThreadSafe

    @Override
    public void onTestStart(ITestResult result) {
       test =  extent.createTest(result.getMethod().getMethodName());
       extentTest.set(test);/*Crea un ID unico por cada test, no se ua el TEST directamente porque si usamos solo el Test en si se sobreescribiria*/
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS,"Test Passed Succesfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get()//con esta linea de codigo tomamos el ID del Thread creado localmente para ser evaluado y sobreescrito
                .fail(result.getThrowable());
        System.out.println("Aqui falla el test" + result.getMethod().getMethodName());

        try {
            System.out.println("Intentando crear el driver");
            driver = (WebDriver) result.getTestClass()
                    .getRealClass()
                    .getField("driver")
                    .get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }


        String filePath = null;
        try {
            System.out.println("creando el screenshot");
            filePath = getScreenshoot(result.getMethod().getMethodName(),driver);
            System.out.println("Screeenshot creado");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Screenshot no creado");
        }

        extentTest.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());

    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        System.out.println("Extent Creado al finalizar");
    }
}
