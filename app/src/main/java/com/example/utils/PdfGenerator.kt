package com.example.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PdfGenerator {
    fun generateProgressCardPdf(context: Context, studentName: String, seatNumber: String) {
        val document = PdfDocument()
        
        // A4 size in PostScript points (1/72 inch). A4 = 8.27 x 11.69 inches -> 595 x 842
        val pageInfo = PdfDocument.PageInfo.Builder(842, 595, 1).create() // Landscape
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            isAntiAlias = true
        }
        val titlePaint = Paint().apply {
            color = Color.rgb(217, 119, 6) // #D97706
            textSize = 18f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        
        val headerPaint = Paint().apply {
            color = Color.BLACK
            textSize = 16f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }

        val borderPaint = Paint().apply {
            color = Color.rgb(209, 213, 219) // #D1D5DB Gray
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        
        val bgPaint = Paint().apply {
            color = Color.rgb(243, 244, 246) // #F3F4F6
            style = Paint.Style.FILL
        }

        // Draw Outer Border
        canvas.drawRoundRect(20f, 20f, 822f, 575f, 16f, 16f, borderPaint)

        // Draw Header
        canvas.drawText("الجمهورية اليمنية", 720f, 50f, headerPaint)
        canvas.drawText("وزارة التربية والتعليم", 720f, 70f, headerPaint)
        canvas.drawText("قطاع المناهج والتوجيه", 720f, 90f, headerPaint)
        canvas.drawText("الإدارة العامة للاختبارات", 720f, 110f, headerPaint)

        titlePaint.color = Color.BLACK
        titlePaint.textSize = 22f
        canvas.drawText("بطاقة التقدم لاختبارات شهادة", 421f, 80f, titlePaint)
        canvas.drawText("إتمام التعليم الأساسي", 421f, 110f, titlePaint)
        titlePaint.textSize = 14f
        canvas.drawText("العام الدراسي 2025 / 2026", 421f, 140f, titlePaint)

        canvas.drawText("محافظة: تعز", 120f, 60f, headerPaint)
        canvas.drawText("مديرية: مشرعة وحدنان", 120f, 90f, headerPaint)
        canvas.drawText("مدرسة: شعيب المجيرين", 120f, 120f, headerPaint)

        // Section 1
        titlePaint.color = Color.rgb(217, 119, 6)
        titlePaint.textAlign = Paint.Align.RIGHT
        canvas.drawText("أولاً: بيانات أساسية للطالب / الطالبة", 800f, 190f, titlePaint)
        
        // Draw Table 1 (Name)
        canvas.drawRect(400f, 200f, 800f, 220f, bgPaint)
        canvas.drawRect(400f, 200f, 800f, 260f, borderPaint)
        canvas.drawLine(400f, 220f, 800f, 220f, borderPaint)
        
        paint.textAlign = Paint.Align.CENTER
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("الاسم بالعربية (رباعياً مع اللقب)", 600f, 215f, paint)

        val colWidth = 80f
        for (i in 0 until 5) {
            val x = 400f + (i * colWidth)
            canvas.drawLine(x, 220f, x, 260f, borderPaint)
        }
        val titles = listOf("اللقب", "الرابع", "الثالث", "الثاني", "الأول")
        val names = studentName.split(" ")
        for (i in 0 until 5) {
            val x = 400f + (i * colWidth) + (colWidth / 2)
            canvas.drawText(titles[i], x, 235f, paint)
            val namePart = if (i < names.size) names[names.size - 1 - i] else ""
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            canvas.drawText(namePart, x, 255f, paint)
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        // Section 2
        canvas.drawText("ثانياً: بيانات خاصة بحالة التقدم", 800f, 320f, titlePaint)
        
        // Section 2 Table
        canvas.drawRect(40f, 330f, 800f, 350f, bgPaint)
        canvas.drawRect(40f, 330f, 800f, 430f, borderPaint)
        canvas.drawLine(40f, 350f, 800f, 350f, borderPaint)
        canvas.drawText("أ - الحالات المتقدمة لأول مرة", 780f, 345f, Paint(titlePaint).apply { textSize = 12f; color=Color.BLACK })
        
        val s2ColWidth = 760f / 4f
        for (i in 1..3) {
            val y = 350f + (i * 20f)
            canvas.drawLine(40f, y, 800f, y, borderPaint)
        }
        for (i in 1..3) {
            val x = 40f + (i * s2ColWidth)
            canvas.drawLine(x, 350f, x, 430f, borderPaint)
        }

        val hTitles = listOf("المديرية", "العام الدراسي", "المدرسة", "الصف")
        val hData = listOf(
            listOf("مشرعة وحدنان", "2022/2023", "شعيب المجيرين", "السادس اساسي"),
            listOf("مشرعة وحدنان", "2023/2024", "شعيب المجيرين", "السابع اساسي"),
            listOf("مشرعة وحدنان", "2024/2025", "شعيب المجيرين", "الثامن اساسي")
        )
        
        for (i in 0 until 4) {
            val x = 40f + (i * s2ColWidth) + (s2ColWidth / 2)
            canvas.drawText(hTitles[i], x, 365f, paint)
            for (j in 0 until 3) {
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                canvas.drawText(hData[j][i], x, 385f + (j * 20f), paint)
            }
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        // Footer Signatures
        canvas.drawText("مدير المدرسة", 700f, 520f, headerPaint)
        canvas.drawText("مدير مكتب التربية بالمديرية", 421f, 520f, headerPaint)
        canvas.drawText("يعتمد / مدير مكتب التربية بالمحافظة", 150f, 520f, headerPaint)


        document.finishPage(page)

        try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, "Progress_Card_$seatNumber.pdf")
            document.writeTo(FileOutputStream(file))
            Toast.makeText(context, "تم حفظ بطاقة التقدم في التنزيلات", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "فشل حفظ الملف", Toast.LENGTH_SHORT).show()
        } finally {
            document.close()
        }
    }
}
