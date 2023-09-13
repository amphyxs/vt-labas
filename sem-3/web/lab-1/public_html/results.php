<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/resultss.css">
    <link rel="icon" type="image/png" href="favicon.png">
    <title>Results</title>
</head>
<body>
    <?php
        ini_set('display_errors', 1);
        ini_set('display_startup_errors', 1);
        error_reporting(E_ALL);

        $time_start = microtime(true);
        session_start();

        class HitData
        {
            public float $x;
            public float $y;
            public float $r;
            public bool $hit;
            public DateTime $date;
            public float $script_work_time;

            public function __construct(float $x, float $y, float $r, float $script_work_time, string $timezone) {
                $this->x = $x;
                $this->y = $y;
                $this->r = $r;
                $this->hit = $this->check_hit($x, $y, $r);
                $this->date = new DateTime('now', new DateTimeZone($timezone));
                $this->script_work_time = $script_work_time;
            }

            public function __toString() {
                $formatted_date = $this->get_formatted_date();
                $formatted_hit = $this->get_formatted_hit();
                $formatted_script_work_time = $this->get_formatted_script_work_time();
                return "
                <tr>
                    <td>
                        $this->x
                    </td>
                    <td>
                        $this->y
                    </td>
                    <td>
                        $this->r
                    </td>
                    <td>
                        $formatted_hit
                    </td>
                    <td>
                        $formatted_date
                    </td>
                    <td>
                        $formatted_script_work_time
                    </td>
                </tr>
                ";
            }

            private function get_formatted_hit(): string {
                if ($this->hit)
                    return "<input id=\"hit-checkbox\" type=\"checkbox\" disabled checked/>";

                return "<input id=\"hit-checkbox\" type=\"checkbox\" disabled/>";
            }

            private function get_formatted_date(): string {
                return $this->date->format('d/m/Y H:i:s');
            }

            private function get_formatted_script_work_time(): string {
                $max_digits = 5;
                $formatted_number = number_format($this->script_work_time, $max_digits);
                return "$formatted_number с.";
            }

            private function check_hit(float $x, float $y, float $r): bool {
                $is_inside_area = false;
                if ($x <= 0 && $y >= 0)  # 2-ая четверть
                    $is_inside_area |= $y <= $x + $r / 2;  # Область под прямой y = x + r/2
                if ($x <= 0 && $y <= 0)  # 3-ая четверть
                    $is_inside_area |= $x**2 + $y**2 <= $r**2;  # Внутри окружности с радиусом r, центром (0;0)
                if ($x >= 0 && $y <= 0)  # 4-я четверть 
                    $is_inside_area |= ($x <= $r) && ($y >= -$r);  # Внутри квадрата r на r

                return $is_inside_area;
            }
        }

        function validate_coords_data(string | null $x, string | null $y, string | null $r): bool 
        {
            $r_expected_values = array(1, 1.5, 2, 2.5, 3);

            $is_x_valid = isset($x) && $x > -3 && $x < 3;
            $is_y_valid = isset($y) && $y > -3 && $y < 3;
            $is_r_valid = isset($r) && in_array($r, $r_expected_values);

            $is_correct = $is_x_valid && $is_y_valid && $is_r_valid;
            return $is_correct;
        }

        function truncate_number(string | null $num_str): string | null
        {
            if (!isset($num_str)) {
                return null;
            }
            $max_digits = 10;
            return substr($num_str, 0, $max_digits);
        }

        function get_data(): array
        {
            $hit_data = $_SESSION['hit_data'] ?? array();
            return $hit_data;
        }

        function update_and_get_data(float $x, float $y, float $r, string $timezone): array
        {
            date_default_timezone_set($timezone);

            global $time_start;

            $hit_data = get_data();
            
            $time_end = microtime(true);
            $execution_time = $time_end - $time_start;
            $hit_data[] = serialize(new HitData($x, $y, $r, $execution_time, $timezone));
            
            $_SESSION['hit_data'] = $hit_data;

            return $hit_data;
        }

        function print_table(array $table_rows, string $timezone): void
        {
            echo "
            <table>
                <thead>
                    <tr>
                        <td>X</td>
                        <td>Y</td>
                        <td>R</td>
                        <td>Попал</td>
                        <td>Дата</td>
                        <td>Время исполнения</td>
                    </tr>
                </thead>
                <tbody>
            ";
            foreach ($table_rows as $key => $row) {
                $object = unserialize($row);
                $object->date->setTimeZone(new DateTimeZone($timezone));
                echo $object;
            }
            echo "
                </tbody>
            </table>
            ";
        }
        
        $x = truncate_number($_POST['x']  ?? null);
        $y = truncate_number($_POST['y'] ?? null);
        $r = truncate_number($_POST['r'] ?? null);
        if (array_key_exists('timezone', $_POST)) {
            $timezone = $_POST['timezone'];
        } else {
            $queries = array();
            parse_str($_SERVER['QUERY_STRING'], $queries);
            $timezone = $queries['timezone'] ?? null;
        }

        if (validate_coords_data($x, $y, $r)) {
            $rows = update_and_get_data($x, $y, $r, $timezone);
            $rows = array_reverse($rows);
            print_table($rows, $timezone);
        } else if ($x == null && $y == null && $r == null) {
            $rows = get_data();
            $rows = array_reverse($rows);
            print_table($rows, $timezone);
        } else {
            http_response_code(400);
            echo "<p>Invalid data</p>";
        }
    ?>
</body>
</html>
