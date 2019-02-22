package tally

import kotlin.math.max
import kotlin.math.min

class Matrix(val rows: List<List<Int>>) {
    private var rowCount: Int = 0
    private var columnCount: Int = 0

    init {
        for (row in rows) {
            rowCount += 1
            if (columnCount == 0) {
                columnCount = row.size
            } else if (columnCount != row.size) {
                throw RuntimeException("all rows must be the same size")
            }
        }
    }

    fun rowCount(): Int = rowCount
    fun columnCount(): Int = columnCount

    operator fun get(rowIndex: Int, columnIndex: Int): Int {
        return rows[rowIndex][columnIndex]
    }

    fun schulzeStrongestPaths(): Matrix {
        val size = squareSize()
        val strongestPaths = createEmptyMutableMatrixData(rowCount, columnCount)
        for (i in 0 until size) {
            for (j in 0 until size) {
                strongestPaths[i][j] = rows[i][j]
            }
        }
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (i != j) {
                    for (k in 0 until size) {
                        if (i != k && j != k) {
                            strongestPaths[j][k] =
                                    max(strongestPaths[j][k], min(strongestPaths[j][i], strongestPaths[i][k]))
                        }
                    }
                }
            }
        }
        return Matrix(strongestPaths)
    }

    fun schulzeTally(): List<List<Int>> = schulzeTally(emptyList(), emptyList())
    private tailrec fun schulzeTally(soFar: List<List<Int>>, indices: List<Int>): List<List<Int>> {
        val size = squareSize()
        return if (indices.size == size) soFar
        else {
            val undefeated = (0 until size).filter { i ->
                !indices.contains(i) && (0 until size).all { j ->
                    indices.contains(j) || get(i, j) >= get(j, i)
                }
            }
            schulzeTally(soFar + listOf(undefeated), indices + undefeated)
        }
    }

    private fun squareSize(): Int =
            if (rowCount == columnCount)
                rowCount
            else
                throw UnsupportedOperationException(
                        "This method is only valid for square matrices," +
                                " this matrix has $rowCount rows and $columnCount columns")


    private fun createEmptyMutableMatrixData(rowCount: Int, columnCount: Int): MutableList<MutableList<Int>> =
            mutableListOf(*(0 until rowCount).map { mutableListOf(*(0 until columnCount).map { 0 }.toTypedArray()) }.toTypedArray())

    companion object {
        fun ofSizeWithDefault(rowCount: Int, columnCount: Int, default: Int): Matrix =
                Matrix(listOf(*(0 until rowCount).map { listOf(*(0 until columnCount).map { default }.toTypedArray()) }.toTypedArray()))

        fun ofSizeWithGenerator(rowCount: Int, columnCount: Int, generate: (Int, Int) -> Int): Matrix {
            val rows = mutableListOf<List<Int>>()
            for (i in 0 until rowCount) {
                val cells = mutableListOf<Int>()
                for (j in 0 until columnCount) {
                    cells.add(generate(i, j))
                }
                rows.add(cells)
            }
            return Matrix(rows)
        }

        operator fun Matrix.plus(that: Matrix): Matrix {
            if (this.rowCount() != that.rowCount())
                throw RuntimeException("Attempting to add a matrix with ${this.rowCount()} rows to matrix with ${that.rowCount()} rows")
            return ofSizeWithGenerator(this.rowCount(), this.columnCount(), { i, j -> this[i, j] + that[i, j] })
        }
    }
}
