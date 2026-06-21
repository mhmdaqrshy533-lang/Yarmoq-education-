package com.example.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * PDF/Certificate Engine: وحدة برمجية مستقلة تتعامل فقط مع مكتبات الـ PDF
 *
 * Uses built-in Android PdfDocument to construct PDFs totally offline, locally.
 */
class LocalPdfEngine(private val context: Context) {

    fun generateDirectCertificateMode(
        studentName: String,
        gradeText: String,
        includeSeal: Boolean
    ): File? {
        val document = PdfDocument()
        
        // A4 size: 595 x 842 points
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        
        val canvas: Canvas = page.canvas
        val paint = Paint()

        // Background styling
        paint.color = Color.WHITE
        canvas.drawRect(0f, 0f, 595f, 842f, paint)
        
        // Border
        paint.color = Color.rgb(218, 165, 32) // Goldenrod
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas.drawRect(20f, 20f, 575f, 822f, paint)

        // Title
        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        paint.textSize = 40f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("شـهـادة تـفـوق", 297.5f, 150f, paint)

        // Content
        paint.textSize = 24f
        canvas.drawText("يسر إدارة المدرسة أن تمنح الطالب:", 297.5f, 250f, paint)
        
        paint.textSize = 36f
        paint.color = Color.rgb(15, 42, 74) // Yarmouk Dark Blue
        canvas.drawText(studentName, 297.5f, 320f, paint)
        
        paint.textSize = 24f
        paint.color = Color.BLACK
        canvas.drawText("تقديراً لتفوقه بتقدير ($gradeText)", 297.5f, 400f, paint)

        if (includeSeal) {
            paint.color = Color.RED
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 3f
            canvas.drawCircle(150f, 650f, 50f, paint)
            
            paint.style = Paint.Style.FILL
            paint.textSize = 16f
            canvas.drawText("ختم المدرسة", 150f, 655f, paint)
        }

        document.finishPage(page)

        // Save
        return try {
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!directory.exists()) directory.mkdirs()
            
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val file = File(directory, "Yarmouk_Certificate_$timeStamp.pdf")
            
            document.writeTo(FileOutputStream(file))
            document.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            document.close()
            null
        }
    }
}
