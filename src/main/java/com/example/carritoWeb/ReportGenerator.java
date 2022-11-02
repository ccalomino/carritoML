package com.example.carritoWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import org.springframework.util.ResourceUtils;

import com.example.carritoWeb.dto.CarritoDto;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportGenerator {

	
	
	// Load invoice jrxml template
    private JasperReport loadTemplate(String report) throws JRException, FileNotFoundException 
    {
        File file = ResourceUtils.getFile("classpath:reports/"+report);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        return jasperReport;
    }
    
    
    
    
    public void generatePdfReportFactura(int id, String report, List<CarritoDto> listaProd, float total1) throws JRException, FileNotFoundException 
    {   	
        JasperReport jreport = this.loadTemplate(report);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaProd);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("idCarrito", id);
        params.put("total1", total1);
        JasperPrint jprint = JasperFillManager.fillReport(jreport, params, ds);
        JasperExportManager.exportReportToPdfFile(jprint,"src/main/resources/reports/factura.pdf");
    }
}
