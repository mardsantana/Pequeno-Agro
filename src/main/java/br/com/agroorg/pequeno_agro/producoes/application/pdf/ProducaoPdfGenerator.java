package br.com.agroorg.pequeno_agro.producoes.application.pdf;

import br.com.agroorg.pequeno_agro.producoes.application.api.ProducaoResponse;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class ProducaoPdfGenerator {

    // Use essa assinatura para receber dados extras do relatório:
    public byte[] gerarPdf(List<ProducaoResponse> producoes, String nomeFazenda, String responsavel,
                           Date dataInicio, Date dataFim, String cidadeEstado, String observacao) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document doc = new Document(PageSize.A4, 36, 36, 80, 50); // margem topo maior para cabeçalho
            PdfWriter writer = PdfWriter.getInstance(doc, out);

            writer.setPageEvent(new HeaderFooterPageEvent()); // rodapé + cabeçalho
            doc.open();

            // 🖼️ Logo da empresa (ajuste o caminho/local real da imagem se necessário)
            try {
                Image logo = Image.getInstance("src/main/resources/static/logo_empresa.png");
                logo.scaleToFit(120, 60);
                logo.setAlignment(Image.ALIGN_CENTER);
                doc.add(logo);
            } catch (Exception e) {
                // Se não tiver logo, apenas ignora
            }

            // ⬆️ Informações estilo "nota fiscal/agricultor" acima do título
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(15);
            infoTable.setWidths(new float[]{3, 7});
            Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            Font valueFont = new Font(Font.FontFamily.HELVETICA, 11);

            infoTable.addCell(createInfoCell("Fazenda:", labelFont));
            infoTable.addCell(createInfoCell(nomeFazenda, valueFont));

            infoTable.addCell(createInfoCell("Responsável:", labelFont));
            infoTable.addCell(createInfoCell(responsavel, valueFont));

            infoTable.addCell(createInfoCell("Período:", labelFont));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String periodo = (dataInicio != null ? sdf.format(dataInicio) : "-") + " até " +
                    (dataFim != null ? sdf.format(dataFim) : "-");
            infoTable.addCell(createInfoCell(periodo, valueFont));

            infoTable.addCell(createInfoCell("Localização:", labelFont));
            infoTable.addCell(createInfoCell(cidadeEstado, valueFont));

            infoTable.addCell(createInfoCell("Observação:", labelFont));
            infoTable.addCell(createInfoCell(observacao != null ? observacao : "-", valueFont));

            doc.add(infoTable);

            // 📝 Título principal
            Font tituloFont = new Font(Font.FontFamily.HELVETICA,   18, Font.BOLD);
            Paragraph titulo = new Paragraph("Relatório de Produções", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingBefore(20);
            titulo.setSpacingAfter(20);
            doc.add(titulo);

            // 📊 Tabela
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 4, 2, 2, 2}); // largura relativa das colunas
            table.setSpacingBefore(10f);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(0, 121, 182); // azul escuro

            Stream.of("Tipo", "Descrição", "Área", "Data Início", "Finalizada").forEach(header -> {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(headerColor);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8);
                table.addCell(cell);
            });

            Font bodyFont = new Font(Font.FontFamily.HELVETICA, 11);
            for (ProducaoResponse p : producoes) {
                table.addCell(createBodyCell(p.getTipo(), bodyFont));
                table.addCell(createBodyCell(p.getDescricao(), bodyFont));
                table.addCell(createBodyCell(String.valueOf(p.getArea()), bodyFont));
                table.addCell(createBodyCell(String.valueOf(p.getDataInicio()), bodyFont));
                table.addCell(createBodyCell(p.getFinalizada() ? "Sim" : "Não", bodyFont));
            }

            doc.add(table);
            doc.close();

            return out.toByteArray();

        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    private PdfPCell createInfoCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(4);
        return cell;
    }

    private PdfPCell createBodyCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    public byte[] gerarPdf(List<ProducaoResponse> producoes) {
        return gerarPdf(producoes,
                "Fazenda Beira Rio",
                "Joaquin Guela funda",
                null,
                null,
                "Rio Verde - GO",
                "Relatório gerado automaticamente");
    }

    // 📄 Classe interna para cabeçalho e rodapé
    class HeaderFooterPageEvent extends PdfPageEventHelper {
        Font rodapeFont = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, BaseColor.GRAY);

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase rodape = new Phrase("Página " + writer.getPageNumber() + " | Gerado em: " +
                    new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()), rodapeFont);

            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    rodape,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }
    }
}
